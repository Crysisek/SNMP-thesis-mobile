package pl.edu.pb.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import pl.edu.pb.common.navigation.NavigationCommand
import pl.edu.pb.common.navigation.NavigationDestination
import pl.edu.pb.common.navigation.NavigationManager
import pl.edu.pb.common.viewmodel.BaseViewModel
import pl.edu.pb.domain.usecase.GetClientsUseCase
import pl.edu.pb.ui.home.HomeIntent.ClientClicked
import pl.edu.pb.ui.home.HomeIntent.GetClients
import pl.edu.pb.ui.home.HomeUiState.PartialState
import pl.edu.pb.ui.home.HomeUiState.PartialState.Error
import pl.edu.pb.ui.home.HomeUiState.PartialState.FetchedClients
import pl.edu.pb.ui.home.HomeUiState.PartialState.InitialLoading
import pl.edu.pb.ui.home.HomeUiState.PartialState.Loading
import pl.edu.pb.ui.home.mapper.toPresentationModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val getClientsUseCase: GetClientsUseCase,
    savedStateHandle: SavedStateHandle,
    homeInitialState: HomeUiState,
) : BaseViewModel<HomeUiState, PartialState, HomeEvent, HomeIntent>(
    savedStateHandle,
    homeInitialState,
) {
    private var nextPageNumber: Int = 0

    init {
        acceptIntent(GetClients)
    }

    override fun mapIntents(intent: HomeIntent): Flow<PartialState> = when (intent) {
        GetClients -> getClients(nextPageNumber)
        is ClientClicked -> onClientClick(intent.id)
    }

    override fun reduceUiState(previousState: HomeUiState, partialState: PartialState): HomeUiState = when (partialState) {
        InitialLoading -> previousState.copy(
            isInitialLoading = true,
            isError = false,
        )
        is FetchedClients -> previousState.copy(
            isInitialLoading = false,
            isLoading = false,
            clients = previousState.clients + partialState.clients,
            isAllClientsFetched = partialState.isAllClientsFetched,
            isError = false,
        )
        is Error -> previousState.copy(
            isInitialLoading = false,
            isError = true,
        )
        Loading -> previousState.copy(
            isLoading = true,
        )
    }

    private fun getClients(page: Int): Flow<PartialState> = flow {
        getClientsUseCase(page)
            .onStart {
                if (page == 0) {
                    emit(InitialLoading)
                } else {
                    emit(Loading)
                    delay(300) // delay to eliminate ui glitches
                }
            }
            .collect { result ->
                result
                    .onSuccess { clientListPaged ->
                        val clients = clientListPaged.clients.map { it.toPresentationModel() }
                        nextPageNumber = clientListPaged.currentPage + 1
                        emit(FetchedClients(clients, page == clientListPaged.totalPages - 1))
                    }
                    .onFailure {
                        emit(Error(it))
                    }
            }
    }

    private fun onClientClick(id: String): Flow<PartialState> {
        navigationManager.navigate(
            object : NavigationCommand {
                override val destination = NavigationDestination.ClientDetails.createClientDetailsRoute(id)
            }
        )
        return emptyFlow()
    }
}