package pl.edu.pb.ui.clientdetails

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import pl.edu.pb.common.navigation.NavigationDestination
import pl.edu.pb.common.navigation.NavigationManager
import pl.edu.pb.common.viewmodel.BaseViewModel
import pl.edu.pb.domain.usecase.GetStatusesUseCase
import pl.edu.pb.ui.clientdetails.ClientDetailsIntent.*
import pl.edu.pb.ui.clientdetails.ClientDetailsUiState.PartialState
import pl.edu.pb.ui.clientdetails.ClientDetailsUiState.PartialState.Error
import pl.edu.pb.ui.clientdetails.ClientDetailsUiState.PartialState.FetchedStatuses
import pl.edu.pb.ui.clientdetails.ClientDetailsUiState.PartialState.InitialLoading
import pl.edu.pb.ui.clientdetails.ClientDetailsUiState.PartialState.Loading
import pl.edu.pb.ui.clientdetails.ClientDetailsUiState.PartialState.SortLoading
import pl.edu.pb.ui.clientdetails.ClientDetailsUiState.PartialState.SortedStatuses
import pl.edu.pb.ui.clientdetails.ClientDetailsUiState.PartialState.UpdateSortByRadioButtons
import pl.edu.pb.ui.clientdetails.ClientDetailsUiState.PartialState.UpdateSortDirRadioButtons
import pl.edu.pb.ui.home.mapper.toPresentationModel
import javax.inject.Inject

@HiltViewModel
class ClientDetailsViewModel @Inject constructor(
    private val getStatusesUseCase: GetStatusesUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager,
    clientDetailsInitialState: ClientDetailsUiState,
) : BaseViewModel<ClientDetailsUiState, PartialState, ClientDetailsEvent, ClientDetailsIntent>(
    savedStateHandle,
    clientDetailsInitialState,
) {
    private var nextPageNumber: Int = 0
    private val id
        get() = savedStateHandle.get<String>(NavigationDestination.ClientDetails.CLIENT_ID)
            ?: throw IllegalStateException("Parameter client ID must not be null!")

    init {
        acceptIntent(GetStatuses)
    }

    override fun mapIntents(intent: ClientDetailsIntent): Flow<PartialState> = when (intent) {
        GetStatuses -> getStatuses(nextPageNumber)
        SortStatuses -> sortStatuses()
        StatusClicked -> onStatusClick()
        BackClicked -> onBackClick()
        is SortByClicked -> onSortByClicked(intent.sortBy)
        is SortDirClicked -> onSortDirClicked(intent.sortDir)
    }

    override fun reduceUiState(previousState: ClientDetailsUiState, partialState: PartialState): ClientDetailsUiState =
        when (partialState) {
            InitialLoading -> previousState.copy(
                isInitialLoading = true,
                isError = false,
            )
            Loading -> previousState.copy(
                isLoading = true,
            )
            SortLoading -> previousState.copy(
                isSortLoading = true,
            )
            is FetchedStatuses -> previousState.copy(
                isInitialLoading = false,
                isLoading = false,
                statuses = previousState.statuses + partialState.statuses,
                isAllStatusesFetched = partialState.isAllStatusesFetched,
                bottomSheetUiState = previousState.bottomSheetUiState.copy(
                    sortByOption = partialState.sortByOptions,
                ),
                isError = false,
            )
            is SortedStatuses -> previousState.copy(
                isSortLoading = false,
                isLoading = false,
                statuses = partialState.statuses,
                isAllStatusesFetched = partialState.isAllStatusesFetched,
                isError = false,
            )
            is UpdateSortByRadioButtons -> previousState.copy(
                bottomSheetUiState = previousState.bottomSheetUiState.copy(
                    sortBy = partialState.sortBy,
                )
            )
            is UpdateSortDirRadioButtons -> previousState.copy(
                bottomSheetUiState = previousState.bottomSheetUiState.copy(
                    sortDir = partialState.sortDir,
                )
            )
            is Error -> previousState.copy(
                isInitialLoading = false,
                isError = true,
            )
        }

    private fun getStatuses(page: Int): Flow<PartialState> = flow {
        val (sortDir, sortBy) = uiState.value.bottomSheetUiState
        getStatusesUseCase(id, page, sortBy, sortDir.name)
            .onStart {
                if (page == 0) {
                    emit(InitialLoading)
                } else {
                    emit(Loading)
                }.also {
                    delay(400) // delay to eliminate ui glitches
                }
            }
            .collect { result ->
                result
                    .onSuccess { statusListPaged ->
                        val statuses = statusListPaged.statuses.map { it.toPresentationModel() }
                        nextPageNumber = statusListPaged.currentPage + 1
                        emit(
                            FetchedStatuses(
                                statuses = statuses,
                                sortByOptions = setOf(BottomSheetUiState.defaultSortBy) + statuses[0].nameStatusPair.keys,
                                isAllStatusesFetched = page == statusListPaged.totalPages - 1
                            ),
                        )
                    }
                    .onFailure {
                        emit(Error(it))
                    }
            }
    }

    private fun sortStatuses(): Flow<PartialState> = flow {
        nextPageNumber = 0
        val (sortDir, sortBy) = uiState.value.bottomSheetUiState
        getStatusesUseCase(id, nextPageNumber, sortBy, sortDir.name)
            .onStart {
                emit(SortLoading)
                delay(400) // delay to eliminate ui glitches
            }
            .collect { result ->
                result
                    .onSuccess { statusListPaged ->
                        val statuses = statusListPaged.statuses.map { it.toPresentationModel() }
                        nextPageNumber = statusListPaged.currentPage + 1
                        emit(
                            SortedStatuses(
                                statuses = statuses.toSet(),
                                isAllStatusesFetched = nextPageNumber == statusListPaged.totalPages
                            ),
                        )
                    }
                    .onFailure {
                        emit(Error(it))
                    }
            }

    }

    private fun onStatusClick(): Flow<PartialState> {
        return emptyFlow()
        TODO("Implement nice looking UI with maybe graphs")
    }

    private fun onSortByClicked(sortBy: String): Flow<PartialState> = flow {
        emit(UpdateSortByRadioButtons(sortBy))
    }

    private fun onSortDirClicked(sortDir: BottomSheetUiState.SortDir): Flow<PartialState> = flow {
        emit(UpdateSortDirRadioButtons(sortDir))
    }

    private fun onBackClick(): Flow<PartialState> {
        navigationManager.navigateUp()
        return emptyFlow()
    }
}