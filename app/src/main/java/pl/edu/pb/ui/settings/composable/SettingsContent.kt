package pl.edu.pb.ui.settings.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun SettingsContent(
    refreshInterval: Float,
    onRefreshIntervalChange: (Float) -> Unit,
    isRefreshOn: Boolean,
    onRefreshSwitcherChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(20.dp),
    ) {
        Text(
            text = "Refresh Interval: ${refreshInterval.toInt()} sec",
        )
        Slider(
            value = refreshInterval,
            enabled = isRefreshOn,
            steps = 11,
            valueRange = 5f..30f,
            onValueChange = onRefreshIntervalChange,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Switch(
                checked = isRefreshOn,
                onCheckedChange = onRefreshSwitcherChange,
            )
            Text(
                text = "Refresh ${if (isRefreshOn)  "enabled" else "disabled"}"
            )
        }
    }
}