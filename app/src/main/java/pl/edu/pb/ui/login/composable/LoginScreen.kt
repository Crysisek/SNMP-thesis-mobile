package pl.edu.pb.ui.login.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.Flow
import pl.edu.pb.common.extensions.collectAsStateWithLifecycle
import pl.edu.pb.common.extensions.collectWithLifecycle
import pl.edu.pb.common.extensions.rememberIntent
import pl.edu.pb.common.extensions.rememberIntentWithParam
import pl.edu.pb.ui.login.LoginEvent
import pl.edu.pb.ui.login.LoginEvent.ShowSnackbarEvent
import pl.edu.pb.ui.login.LoginIntent.CheckUriCorrectness
import pl.edu.pb.ui.login.LoginIntent.DialogConfirmClicked
import pl.edu.pb.ui.login.LoginIntent.DialogDismissClicked
import pl.edu.pb.ui.login.LoginIntent.LoginClicked
import pl.edu.pb.ui.login.LoginIntent.PasswordChanged
import pl.edu.pb.ui.login.LoginIntent.UriChanged
import pl.edu.pb.ui.login.LoginIntent.UsernameChanged
import pl.edu.pb.ui.login.LoginUiState
import pl.edu.pb.ui.login.LoginViewModel

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val focusRequester = remember { FocusRequester() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HandleEvents(viewModel.event, viewModel::setSnackbarVisibility, snackbarHostState, focusRequester)

    LoginScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        focusRequester = focusRequester,
        onUriChanged = rememberIntentWithParam(viewModel) {
            UriChanged(it)
        },
        checkUriCorrectness = rememberIntent(viewModel) {
            CheckUriCorrectness
        },
        onUsernameChanged = rememberIntentWithParam(viewModel) {
            UsernameChanged(it)
        },
        onPasswordChanged = rememberIntentWithParam(viewModel) {
            PasswordChanged(it)
        },
        onLoginClick = rememberIntent(viewModel) {
            LoginClicked
        },
        onDialogConfirmClick = rememberIntent(viewModel) {
            DialogConfirmClicked
        },
        onDialogDismissClick = rememberIntent(viewModel) {
            DialogDismissClicked
        },
        modifier = modifier.fillMaxSize(),
    )
}

@Composable
private fun LoginScreen(
    uiState: LoginUiState,
    snackbarHostState: SnackbarHostState,
    focusRequester: FocusRequester,
    checkUriCorrectness: () -> Unit,
    onUriChanged: (String) -> Unit,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClick: () -> Unit,
    onDialogConfirmClick: () -> Unit,
    onDialogDismissClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier,
    ) { paddingValues ->
        when {
            uiState.isLoading -> LoginLoadingContent(
                isDialogVisible = uiState.isDialogVisible,
                onConfirm = onDialogConfirmClick,
                onDismiss = onDialogDismissClick,
                modifier = modifier,
            )
            else -> LoginContent(
                isUriCorrect = uiState.isUriCorrect,
                uri = uiState.uri,
                onUriChanged = onUriChanged,
                checkUriCorrectness = checkUriCorrectness,
                isSuccessfullyConnected = uiState.isSuccessfullyConnected,
                username = uiState.username,
                onUsernameChanged = onUsernameChanged,
                password = uiState.password,
                onPasswordChanged = onPasswordChanged,
                isLoginError = uiState.isLoginError,
                onLoginClick = onLoginClick,
                focusRequester = focusRequester,
                modifier = modifier.padding(paddingValues),
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun HandleEvents(
    events: Flow<LoginEvent>,
    setSnackbarVisibility: (Boolean) -> Unit,
    snackbarHostState: SnackbarHostState,
    focusRequester: FocusRequester,
) {
    val localKeyboardController = LocalSoftwareKeyboardController.current

    events.collectWithLifecycle {
        when (it) {
            is ShowSnackbarEvent -> {
                localKeyboardController?.hide()
                setSnackbarVisibility(true)
                snackbarHostState.showSnackbar(
                    message = it.message,
                    actionLabel = "FIX",
                    withDismissAction = true,
                ).let { snackbarResult ->
                    when (snackbarResult) {
                        SnackbarResult.ActionPerformed -> {
                            focusRequester.requestFocus()
                        }
                        SnackbarResult.Dismissed -> {}
                    }.also { setSnackbarVisibility(false) }
                }
            }
        }
    }
}