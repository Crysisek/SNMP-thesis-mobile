package pl.edu.pb.ui.login.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import pl.edu.pb.R

@Composable
internal fun LoginContent(
    isUriCorrect: Boolean,
    uri: String,
    onUriChanged: (String) -> Unit,
    checkUriCorrectness: () -> Unit,
    isSuccessfullyConnected: Boolean,
    username: String,
    onUsernameChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    onLoginClick: () -> Unit,
    isLoginError: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .width(IntrinsicSize.Max)
            .padding(40.dp),
    ) {
        var uriTextFieldHasFocus: Boolean by remember { mutableStateOf(false) }
        LoginTextField(
            textValue = uri,
            onTextChanged = onUriChanged,
            label = R.string.url_label,
            hint = R.string.url_help,
            imeAction = ImeAction.Next,
            isError = !isUriCorrect,
            isSuccessfullyConnected = isSuccessfullyConnected,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if (isUriCorrect && uriTextFieldHasFocus && it.isFocused.not()) {
                        checkUriCorrectness()
                    }
                    uriTextFieldHasFocus = it.isFocused
                },
        )
        LoginTextField(
            textValue = username,
            onTextChanged = onUsernameChanged,
            label = R.string.username_label,
            hint = R.string.username_help,
            imeAction = ImeAction.Next,
            isError = isLoginError,
            modifier = Modifier.fillMaxWidth(),
        )
        LoginTextField(
            textValue = password,
            onTextChanged = onPasswordChanged,
            label = R.string.password_label,
            hint = R.string.password_help,
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
            isPassword = true,
            isError = isLoginError,
            modifier = Modifier.fillMaxWidth(),
        )

        val isLoginButtonEnabled = uri.isNotBlank() && username.isNotBlank() && password.isNotBlank()
        OutlinedButton(
            onClick = onLoginClick,
            enabled = isLoginButtonEnabled,
        ) {
            Text(
                text = stringResource(id = R.string.login_button)
            )
        }
    }
}

@Composable
private fun LoginTextField(
    textValue: String,
    onTextChanged: (String) -> Unit,
    @StringRes label: Int,
    @StringRes hint: Int,
    imeAction: ImeAction,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isError: Boolean = false,
    isSuccessfullyConnected: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    OutlinedTextField(
        value = textValue,
        onValueChange = onTextChanged,
        singleLine = true,
        label = {
            Text(
                text = stringResource(id = label),
                style = MaterialTheme.typography.labelSmall,
            )
        },
        placeholder = {
            Text(
                text = stringResource(id = hint),
                style = MaterialTheme.typography.bodySmall,
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType,
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        isError = isError,
        colors = if (isSuccessfullyConnected) TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Green,
            unfocusedBorderColor = Color.Green,
        ) else TextFieldDefaults.outlinedTextFieldColors(),
        modifier = modifier,
    )
}