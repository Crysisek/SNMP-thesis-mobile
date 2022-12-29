package pl.edu.pb.ui.search

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import pl.edu.pb.common.navigation.NavigationCommand
import pl.edu.pb.common.navigation.NavigationDestination
import pl.edu.pb.common.navigation.NavigationManager
import pl.edu.pb.common.viewmodel.BaseViewModel
import pl.edu.pb.domain.usecase.SearchForClientsUseCase
import pl.edu.pb.ui.home.mapper.toPresentationModel
import pl.edu.pb.ui.search.SearchIntent.ClientClicked
import pl.edu.pb.ui.search.SearchIntent.QueryChanged
import pl.edu.pb.ui.search.SearchIntent.QueryCleared
import pl.edu.pb.ui.search.SearchIntent.SearchFocusChanged
import pl.edu.pb.ui.search.SearchUiState.PartialState
import pl.edu.pb.ui.search.SearchUiState.PartialState.AfterQueryChanged
import pl.edu.pb.ui.search.SearchUiState.PartialState.AfterSearchFocusChanged
import pl.edu.pb.ui.search.SearchUiState.PartialState.AfterSearching
import pl.edu.pb.ui.search.SearchUiState.PartialState.Error
import pl.edu.pb.ui.search.SearchUiState.PartialState.SearchLoading
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val searchForClientsUseCase: SearchForClientsUseCase,
    savedStateHandle: SavedStateHandle,
    searchInitialState: SearchUiState,
) : BaseViewModel<SearchUiState, PartialState, SearchEvent, SearchIntent>(
    savedStateHandle,
    searchInitialState,
) {

    override fun mapIntents(intent: SearchIntent): Flow<PartialState> = when (intent) {
        QueryCleared -> onClearQuery()
        is QueryChanged -> onQueryChange(intent.query)
        is SearchFocusChanged -> onSearchFocusChange(intent.isFocused)
        is ClientClicked -> onClientClick(intent.id)
    }

    override fun reduceUiState(previousState: SearchUiState, partialState: PartialState): SearchUiState = when (partialState) {
        is SearchLoading -> previousState.copy(
            isSearching = partialState.isSearching,
        )
        is AfterQueryChanged -> previousState.copy(
            query = partialState.query,
            clients = emptySet(),
        )
        is AfterSearchFocusChanged -> previousState.copy(
            isSearchBarFocused = partialState.isFocused,
        )
        is AfterSearching -> previousState.copy(
            isLoading = false,
            clients = partialState.clients,
            isError = false,
        )
        is Error -> previousState.copy(
            isLoading = false,
            isError = true,
        )
    }

    private fun onSearchFocusChange(isFocused: Boolean): Flow<PartialState> = flow {
        emit(AfterSearchFocusChanged(isFocused))
    }

    private fun onQueryChange(query: String): Flow<PartialState> = flow {
        emit(AfterQueryChanged(query))
        if (query.isNotEmpty()) {
            searchForClientsUseCase(query)
                .onStart {
                    emit(SearchLoading(true))
                    delay(100) // delay to eliminate ui glitches
                }
                .onCompletion {
                    emit(SearchLoading(false))
                }
                .collect { result ->
                    result
                        .onSuccess { clients ->
                            emit(
                                AfterSearching(
                                    clients = clients.map { it.toPresentationModel() }.toSet(),
                                )
                            )
                        }
                        .onFailure {
                            emit(Error(it))
                        }
                }
        }
    }

    private fun onClearQuery(): Flow<PartialState> = flow {
        emit(SearchLoading(true))
        delay(100)
        emit(AfterQueryChanged(""))
        emit(SearchLoading(false))
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