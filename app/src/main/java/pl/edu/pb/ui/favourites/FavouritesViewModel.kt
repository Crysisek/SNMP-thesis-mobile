package pl.edu.pb.ui.favourites

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import pl.edu.pb.common.viewmodel.BaseViewModel
import pl.edu.pb.ui.favourites.FavouritesUiState.PartialState
import pl.edu.pb.ui.favourites.FavouritesUiState.PartialState.Error
import pl.edu.pb.ui.favourites.FavouritesUiState.PartialState.Loading
import pl.edu.pb.ui.favourites.FavouritesUiState.PartialState.Success
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    favouritesInitialState: FavouritesUiState,
) : BaseViewModel<FavouritesUiState, PartialState, FavouritesEvent, FavouritesIntent>(
    savedStateHandle,
    favouritesInitialState,
) {
    override fun mapIntents(intent: FavouritesIntent): Flow<PartialState> {
        TODO("Not yet implemented")
    }

    override fun reduceUiState(previousState: FavouritesUiState, partialState: PartialState): FavouritesUiState = when (partialState) {
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