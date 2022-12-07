package pl.edu.pb.ui.settings

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class SettingsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
): Parcelable {

    sealed class PartialState {
        object Loading : PartialState()

        object Success : PartialState()

        data class Error(val throwable: Throwable) : PartialState()
    }
}