package pl.edu.pb.ui.login

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import pl.edu.pb.common.di.ScopeIO
import pl.edu.pb.common.navigation.NavigationCommand
import pl.edu.pb.common.navigation.NavigationDestination
import pl.edu.pb.common.navigation.NavigationManager
import pl.edu.pb.common.network.DynamicUrlInterceptor
import pl.edu.pb.common.viewmodel.BaseViewModel
import pl.edu.pb.domain.usecase.CheckServerUpUseCase
import pl.edu.pb.domain.usecase.GetCredentialsUseCase
import pl.edu.pb.domain.usecase.LoginUseCase
import pl.edu.pb.domain.usecase.SaveCredentialsUseCase
import pl.edu.pb.ui.login.LoginEvent.ShowSnackbarEvent
import pl.edu.pb.ui.login.LoginIntent.CheckUriCorrectness
import pl.edu.pb.ui.login.LoginIntent.DialogConfirmClicked
import pl.edu.pb.ui.login.LoginIntent.DialogDismissClicked
import pl.edu.pb.ui.login.LoginIntent.LoginClicked
import pl.edu.pb.ui.login.LoginIntent.PasswordChanged
import pl.edu.pb.ui.login.LoginIntent.UriChanged
import pl.edu.pb.ui.login.LoginIntent.UsernameChanged
import pl.edu.pb.ui.login.LoginUiState.PartialState
import pl.edu.pb.ui.login.LoginUiState.PartialState.AfterPasswordChanged
import pl.edu.pb.ui.login.LoginUiState.PartialState.AfterUriChanged
import pl.edu.pb.ui.login.LoginUiState.PartialState.AfterUsernameChanged
import pl.edu.pb.ui.login.LoginUiState.PartialState.DialogShown
import pl.edu.pb.ui.login.LoginUiState.PartialState.Error
import pl.edu.pb.ui.login.LoginUiState.PartialState.Loading
import pl.edu.pb.ui.login.LoginUiState.PartialState.UriSuccessfullyConnected
import pl.edu.pb.ui.login.exceptions.LoginException
import pl.edu.pb.ui.login.exceptions.Reason
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ScopeIO private val scopeIO: CoroutineScope,
    private val navigationManager: NavigationManager,
    private val dynamicUrlInterceptor: DynamicUrlInterceptor,
    private val checkServerUpUseCase: CheckServerUpUseCase,
    private val loginUseCase: LoginUseCase,
    private val saveCredentialsUseCase: SaveCredentialsUseCase,
    private val getCredentialsUseCase: GetCredentialsUseCase,
    savedStateHandle: SavedStateHandle,
    loginInitialState: LoginUiState,
) : BaseViewModel<LoginUiState, PartialState, LoginEvent, LoginIntent>(
    savedStateHandle,
    loginInitialState,
) {
    private val uriRegex = Regex("(https?://.*):(\\d*)/?(.*)")
    private var isSnackbarVisible = false
    private lateinit var savedCredentials: List<String>

    init {
        scopeIO.launch {
            getCredentialsUseCase(keys)
                .onSuccess {
                    listOf(
                        UriChanged(it[0]),
                        CheckUriCorrectness,
                        UsernameChanged(it[1]),
                        PasswordChanged(it[2]),
                    ).forEach { intent ->
                        acceptIntent(intent)
                    }
                    savedCredentials = it
                }
                .onFailure {
                    savedCredentials = emptyList()
                }
        }
    }

    override fun mapIntents(intent: LoginIntent): Flow<PartialState> = when (intent) {
        is UriChanged -> updateUri(intent.uri)
        is CheckUriCorrectness -> checkUriCorrectness()
        is UsernameChanged -> updateUsername(intent.username)
        is PasswordChanged -> updatePassword(intent.password)
        is LoginClicked -> onLoginClick()
        is DialogConfirmClicked -> onConfirmDialogClick()
        is DialogDismissClicked -> navigateToHomeScreen()
    }

    override fun reduceUiState(previousState: LoginUiState, partialState: PartialState): LoginUiState = when (partialState) {
        is Loading -> previousState.copy(
            isLoading = true,
            isLoginError = false,
        )
        is DialogShown -> previousState.copy(
            isDialogVisible = true,
        )
        is AfterUriChanged -> previousState.copy(
            uri = partialState.uri,
            isUriCorrect = true,
            isSuccessfullyConnected = false,
        )
        is UriSuccessfullyConnected -> previousState.copy(
            isUriCorrect = true,
            isSuccessfullyConnected = true,
        )
        is AfterUsernameChanged -> previousState.copy(
            username = partialState.username,
            isLoginError = false,
        )
        is AfterPasswordChanged -> previousState.copy(
            password = partialState.password,
            isLoginError = false,
        )
        is Error -> {
            if (!isSnackbarVisible) {
                publicEvent(ShowSnackbarEvent(partialState.throwable.message ?: "Something went wrong"))
            }
            previousState.copy(
                isLoading = false,
                isLoginError = partialState.throwable.reason is Reason.AuthenticationFailed,
                isUriCorrect = partialState.throwable.reason !in listOf(
                    Reason.IncorrectUri,
                    Reason.ServerDoesNotExist,
                ),
                isSuccessfullyConnected = partialState.throwable.reason !is Reason.IncorrectUri,
            )
        }
    }

    fun setSnackbarVisibility(visibility: Boolean) {
        isSnackbarVisible = visibility
    }

    private fun updateUri(uri: String): Flow<PartialState> = flow {
        emit(AfterUriChanged(uri))
    }

    private fun updateUsername(username: String): Flow<PartialState> = flow {
        emit(AfterUsernameChanged(username = username))
    }

    private fun updatePassword(password: String): Flow<PartialState> = flow {
        emit(AfterPasswordChanged(password = password))
    }

    private fun checkUriCorrectness(): Flow<PartialState> = flow {
        if (uiState.value.uri.isNotEmpty() && !uriRegex.matches(uiState.value.uri)) {
            val throwable = LoginException(Reason.IncorrectUri)
            emit(Error(throwable))
        } else if (uiState.value.uri.isNotEmpty()) {
            dynamicUrlInterceptor.apply {
                setUrl(uiState.value.uri)
            }
            checkServerUpUseCase()
                .onSuccess {
                    if (it) emit(UriSuccessfullyConnected)
                    else emit(Error(LoginException(Reason.ServerDown)))

                }
                .onFailure {
                    emit(Error(LoginException(Reason.ServerDoesNotExist)))
                }
        }
    }

    private fun onLoginClick(): Flow<PartialState> = flow {
        acceptIntent(CheckUriCorrectness)
        emit(Loading)
        dynamicUrlInterceptor.apply {
            uiState.value.let {
                setCredentials(it.username, it.password)
            }
        }
        loginUseCase()
            .onSuccess {
                if (savedCredentials.isNotEmpty() && uiState.value.toCredentials() == savedCredentials) {
                    navigateToHomeScreen()
                } else {
                    emit(DialogShown)
                }
            }
            .onFailure {
                emit(Error(LoginException(Reason.AuthenticationFailed)))
            }
    }

    private fun onConfirmDialogClick(): Flow<PartialState> = flow {
        saveCredentialsUseCase(keys, uiState.value.toCredentials())
            .onSuccess {
                navigateToHomeScreen()
            }
    }

    private fun navigateToHomeScreen(): Flow<PartialState> {
        navigationManager.navigate(object : NavigationCommand {
            override val destination = NavigationDestination.Home.route
            override val configuration
                get() = NavOptions.Builder()
                    .setPopUpTo(NavigationDestination.Login.route, true)
                    .build()
        })
        return emptyFlow()
    }

    private fun LoginUiState.toCredentials() = listOf(
        uri,
        username,
        password,
    )

    private companion object {
        private const val URI_KEY = "URI_KEY"
        private const val USER_KEY = "USER_KEY"
        private const val PASS_KEY = "PASS_KEY"
        private val keys = listOf(URI_KEY, USER_KEY, PASS_KEY)
    }
}