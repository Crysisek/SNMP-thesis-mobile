package pl.edu.pb.ui.login

sealed class LoginEvent {
    data class ShowSnackbarEvent(val message: String) : LoginEvent()
}