package pl.edu.pb.ui.login.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.edu.pb.R
import pl.edu.pb.common.ui.composable.LoadingIndicator

@Composable
internal fun LoginLoadingContent(
    isDialogVisible: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LoadingIndicator(modifier = modifier) {
        if (isDialogVisible) {
            AlertDialog(
                onDismissRequest = onDismiss,
                confirmButton = {
                    TextButton(onClick = onConfirm,) {
                        Text(text = stringResource(id = R.string.dialog_confirm_button))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray.copy(alpha = 0.7f)),
                    ) {
                        Text(text = stringResource(id = R.string.dialog_dismiss_button))
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.dialog_title))
                },
                text = {
                    Text(text = stringResource(id = R.string.dialog_description))
                }
            )
        }
    }
}