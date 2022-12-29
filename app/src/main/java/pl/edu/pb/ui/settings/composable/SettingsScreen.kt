package pl.edu.pb.ui.settings.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.Flow
import pl.edu.pb.common.extensions.collectAsStateWithLifecycle
import pl.edu.pb.common.extensions.collectWithLifecycle
import pl.edu.pb.common.extensions.rememberIntentWithParam
import pl.edu.pb.ui.settings.SettingsEvent
import pl.edu.pb.ui.settings.SettingsIntent
import pl.edu.pb.ui.settings.SettingsIntent.ChangeRefreshInterval
import pl.edu.pb.ui.settings.SettingsIntent.ChangeRefreshState
import pl.edu.pb.ui.settings.SettingsUiState
import pl.edu.pb.ui.settings.SettingsViewModel

@Composable
fun SettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    HandleEvents(viewModel.event)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        uiState = uiState,
        onRefreshIntervalChange = rememberIntentWithParam(viewModel) {
            ChangeRefreshInterval(it)
        },
        onRefreshSwitcherChange = rememberIntentWithParam(viewModel) {
            ChangeRefreshState(it)
        },
        modifier = modifier.fillMaxSize(),
    )
}

@Composable
private fun SettingsScreen(
    uiState: SettingsUiState,
    onRefreshIntervalChange: (Float) -> Unit,
    onRefreshSwitcherChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingsContent(
        refreshInterval = uiState.refreshInterval,
        onRefreshIntervalChange = onRefreshIntervalChange,
        isRefreshOn = uiState.isRefreshOn,
        onRefreshSwitcherChange = onRefreshSwitcherChange,
        modifier = modifier,
    )
}

@Composable
private fun HandleEvents(events: Flow<SettingsEvent>) {
    events.collectWithLifecycle {
    }
}