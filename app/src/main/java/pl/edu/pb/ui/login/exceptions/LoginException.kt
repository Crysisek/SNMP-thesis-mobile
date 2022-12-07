package pl.edu.pb.ui.login.exceptions

import java.io.IOException

class LoginException(val reason: Reason) : IOException(reason.message)

sealed class Reason(val message: String) {
    object IncorrectUri: Reason("Provided URL does not pass the URL criteria")
    object ServerDown: Reason("Server under provided URL is down")
    object ServerDoesNotExist: Reason("Server under provided URL does not exist")
    object AuthenticationFailed: Reason("Wrong username or password")
}