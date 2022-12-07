package pl.edu.pb.ui.login

sealed class LoginIntent {
    data class UriChanged(val uri: String) : LoginIntent()
    object CheckUriCorrectness : LoginIntent()
    data class UsernameChanged(val username: String) : LoginIntent()
    data class PasswordChanged(val password: String) : LoginIntent()
    object LoginClicked : LoginIntent()
    object DialogConfirmClicked : LoginIntent()
    object DialogDismissClicked : LoginIntent()
}