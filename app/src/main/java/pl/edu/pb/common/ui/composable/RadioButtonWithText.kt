package pl.edu.pb.common.ui.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun RadioButtonWithText(
    text: String,
    isSelected: Boolean,
    onRadioButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .selectable(
                selected = isSelected,
                onClick = { onRadioButtonClicked() },
                role = Role.RadioButton,
            )
            .padding(8.dp),
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null,
        )
        Text(
            text = text,
        )
    }
}
