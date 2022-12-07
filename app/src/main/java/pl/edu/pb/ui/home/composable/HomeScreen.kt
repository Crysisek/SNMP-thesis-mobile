package pl.edu.pb.ui.home.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.Flow
import pl.edu.pb.common.extensions.collectAsStateWithLifecycle
import pl.edu.pb.common.extensions.collectWithLifecycle
import pl.edu.pb.ui.home.HomeEvent
import pl.edu.pb.ui.home.HomeUiState
import pl.edu.pb.ui.home.HomeViewModel

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    HandleEvents(viewModel.event)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
) {
    when {
        uiState.isLoading -> HomeLoadingContent()
        else -> HomeContent(
            clients = uiState.clients
        )
    }
}

@Composable
private fun HandleEvents(events: Flow<HomeEvent>) {
    events.collectWithLifecycle {
    }
}