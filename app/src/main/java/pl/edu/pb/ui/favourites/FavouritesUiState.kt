package pl.edu.pb.ui.favourites

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class FavouritesUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
): Parcelable {

    sealed class PartialState {
        object Loading : PartialState()

        object Success : PartialState()

        data class Error(val throwable: Throwable) : PartialState()
    }
}