package pl.edu.pb.ui.settings

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class SettingsUiState(
    val isRefreshOn: Boolean = true,
    val refreshInterval: Float = 5f,
    val isError: Boolean = false,
): Parcelable {

    sealed class PartialState {
        data class RefreshIntervalChanged(val refreshInterval: Float) : PartialState()

        data class RefreshStateChanged(val isRefreshOn: Boolean) : PartialState()

        data class Error(val throwable: Throwable) : PartialState()
    }
}