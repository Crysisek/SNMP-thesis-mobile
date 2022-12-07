package pl.edu.pb.ui.login

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import pl.edu.pb.ui.login.exceptions.LoginException

@Immutable
@Parcelize
data class LoginUiState(
    val uri: String = "",
    val isUriCorrect: Boolean = true,
    val isSuccessfullyConnected: Boolean = false,
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoginError: Boolean = false,
    val isDialogVisible: Boolean = false,
) : Parcelable {

    sealed class PartialState {
        object Loading : PartialState()

        data class AfterUriChanged(val uri: String) : PartialState()

        object UriSuccessfullyConnected : PartialState()

        data class AfterUsernameChanged(val username: String) : PartialState()

        data class AfterPasswordChanged(val password: String) : PartialState()

        object DialogShown : PartialState()

        data class Error(val throwable: LoginException) : PartialState()
    }
}