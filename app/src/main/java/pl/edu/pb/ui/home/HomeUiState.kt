package pl.edu.pb.ui.home

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import pl.edu.pb.ui.home.model.ClientDisplayable

@Immutable
@Parcelize
data class HomeUiState(
    val isInitialLoading: Boolean = false,
    val isLoading: Boolean = false,
    val clients: List<ClientDisplayable> = emptyList(),
    val isAllClientsFetched: Boolean = false,
    val isError: Boolean = false,
): Parcelable {

    sealed class PartialState {
        object InitialLoading : PartialState()

        object Loading : PartialState()

        data class FetchedClients(val clients: List<ClientDisplayable>, val isAllClientsFetched: Boolean) : PartialState()

        data class RefreshedClients(val clients: List<ClientDisplayable>, val isAllClientsFetched: Boolean) : PartialState()

        data class Error(val throwable: Throwable) : PartialState()
    }
}