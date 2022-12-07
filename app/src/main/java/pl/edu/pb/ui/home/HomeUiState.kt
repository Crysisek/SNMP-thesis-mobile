package pl.edu.pb.ui.home

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import pl.edu.pb.data.remote.model.PageInfoResponse
import pl.edu.pb.ui.home.model.ClientDisplayable

@Immutable
@Parcelize
data class HomeUiState(
    val isLoading: Boolean = false,
    val clients: List<ClientDisplayable> = emptyList(),
    val nextPageNumber: Int = 0,
    val isError: Boolean = false,
): Parcelable {

    sealed class PartialState {
        object Loading : PartialState()

        data class FetchedClients(val clients: List<ClientDisplayable>) : PartialState()

        data class Error(val throwable: Throwable) : PartialState()
    }
}