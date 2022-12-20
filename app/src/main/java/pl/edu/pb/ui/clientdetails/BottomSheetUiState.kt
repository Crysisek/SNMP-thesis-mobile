package pl.edu.pb.ui.clientdetails

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import pl.edu.pb.R
import javax.annotation.concurrent.Immutable

@Immutable
@Parcelize
data class BottomSheetUiState(
    val sortDir: SortDir = SortDir.DESC,
    val sortBy: String = defaultSortBy,
    val sortByOption: Set<String> = emptySet(),
) : Parcelable {

    enum class SortDir {
        ASC, DESC,
    }

    companion object {
        const val defaultSortBy = "receivingTime"
    }
}