package pl.edu.pb.ui.search

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import pl.edu.pb.common.viewmodel.BaseViewModel
import pl.edu.pb.ui.search.SearchUiState.PartialState
import pl.edu.pb.ui.search.SearchUiState.PartialState.Error
import pl.edu.pb.ui.search.SearchUiState.PartialState.Loading
import pl.edu.pb.ui.search.SearchUiState.PartialState.Success
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    searchInitialState: SearchUiState,
) : BaseViewModel<SearchUiState, PartialState, SearchEvent, SearchIntent>(
    savedStateHandle,
    searchInitialState,
) {
    override fun mapIntents(intent: SearchIntent): Flow<PartialState> {
        TODO("Not yet implemented")
    }

    override fun reduceUiState(previousState: SearchUiState, partialState: PartialState): SearchUiState = when (partialState) {
        is Loading -> previousState.copy(
            isLoading = true,
            isError = false,
        )
        is Success -> previousState.copy(
            isLoading = false,
            isError = false,
        )
        is Error -> previousState.copy(
            isLoading = false,
            isError = true,
        )
    }
}