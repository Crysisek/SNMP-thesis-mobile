package pl.edu.pb.ui.home

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import pl.edu.pb.common.viewmodel.BaseViewModel
import pl.edu.pb.domain.usecase.GetClientsUseCase
import pl.edu.pb.ui.home.HomeIntent.GetClients
import pl.edu.pb.ui.home.HomeUiState.PartialState
import pl.edu.pb.ui.home.HomeUiState.PartialState.Error
import pl.edu.pb.ui.home.HomeUiState.PartialState.FetchedClients
import pl.edu.pb.ui.home.HomeUiState.PartialState.Loading
import pl.edu.pb.ui.home.mapper.toPresentationModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getClientsUseCase: GetClientsUseCase,
    savedStateHandle: SavedStateHandle,
    homeInitialState: HomeUiState,
) : BaseViewModel<HomeUiState, PartialState, HomeEvent, HomeIntent>(
    savedStateHandle,
    homeInitialState,
) {
    init {
        acceptIntent(GetClients)
    }

    override fun mapIntents(intent: HomeIntent): Flow<PartialState>  = when (intent) {
        is GetClients -> getClients()
    }

    override fun reduceUiState(previousState: HomeUiState, partialState: PartialState): HomeUiState = when (partialState) {
        is Loading -> previousState.copy(
            isLoading = true,
            isError = false,
        )
        is FetchedClients -> previousState.copy(
            isLoading = false,
            clients = partialState.clients,
            isError = false,
        )
        is Error -> previousState.copy(
            isLoading = false,
            isError = true,
        )
    }

    private fun getClients(): Flow<PartialState> = flow {
        getClientsUseCase(uiState.value.nextPageNumber)
            .onStart {
                emit(Loading)
            }
            .collect { result ->
                result
                    .onSuccess { clients ->
                        emit(FetchedClients(clients.map { it.toPresentationModel() }))
                    }
                    .onFailure {
                        emit(Error(it))
                    }
            }
    }
}