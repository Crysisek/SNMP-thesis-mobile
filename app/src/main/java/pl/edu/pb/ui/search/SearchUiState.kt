package pl.edu.pb.ui.search

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import pl.edu.pb.ui.home.model.ClientDisplayable

@Immutable
@Parcelize
data class SearchUiState(
    val isLoading: Boolean = false,
    val query: String = "",
    val isSearchBarFocused: Boolean = false,
    val isSearching: Boolean = false,
    val clients: Set<ClientDisplayable> = emptySet(),
    val isError: Boolean = false,
): Parcelable {

    sealed class PartialState {
        data class SearchLoading(val isSearching: Boolean) : PartialState()

        data class AfterSearching(val clients: Set<ClientDisplayable>) : PartialState()

        data class AfterQueryChanged(val query: String) : PartialState()

        data class AfterSearchFocusChanged(val isFocused: Boolean) : PartialState()

        data class Error(val throwable: Throwable) : PartialState()
    }
}