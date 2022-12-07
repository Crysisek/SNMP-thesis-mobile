package pl.edu.pb.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.Flow
import pl.edu.pb.common.extensions.collectAsStateWithLifecycle
import pl.edu.pb.common.extensions.collectWithLifecycle

@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    HandleEvents(viewModel.event)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

}

@Composable
private fun HandleEvents(events: Flow<SettingsEvent>) {
    events.collectWithLifecycle {
    }
}