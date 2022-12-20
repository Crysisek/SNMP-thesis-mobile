package pl.edu.pb.ui.clientdetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pl.edu.pb.ui.clientdetails.model.StatusDisplayable
import javax.annotation.concurrent.Immutable

@Immutable
@Parcelize
data class ClientDetailsUiState(
    val isInitialLoading: Boolean = false,
    val isLoading: Boolean = false,
    val isSortLoading: Boolean = false,
    val statuses: Set<StatusDisplayable> = emptySet(),
    val isAllStatusesFetched: Boolean = false,
    val isError: Boolean = false,
    val bottomSheetUiState: BottomSheetUiState = BottomSheetUiState(),
) : Parcelable {

    sealed class PartialState {
        object InitialLoading : PartialState()

        object Loading : PartialState()

        object SortLoading : PartialState()

        data class FetchedStatuses(
            val statuses: List<StatusDisplayable>,
            val sortByOptions: Set<String>,
            val isAllStatusesFetched: Boolean
        ) : PartialState()

        data class SortedStatuses(
            val statuses: Set<StatusDisplayable>,
            val isAllStatusesFetched: Boolean,
        ) : PartialState()

        data class UpdateSortByRadioButtons(val sortBy: String) : PartialState()

        data class UpdateSortDirRadioButtons(val sortDir: BottomSheetUiState.SortDir) : PartialState()

        data class Error(val throwable: Throwable) : PartialState()
    }
}