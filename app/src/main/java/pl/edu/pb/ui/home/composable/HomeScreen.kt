package pl.edu.pb.ui.home.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.Flow
import pl.edu.pb.common.extensions.collectAsStateWithLifecycle
import pl.edu.pb.common.extensions.collectWithLifecycle
import pl.edu.pb.common.extensions.rememberIntent
import pl.edu.pb.common.extensions.rememberIntentWithParam
import pl.edu.pb.ui.home.HomeEvent
import pl.edu.pb.ui.home.HomeIntent
import pl.edu.pb.ui.home.HomeIntent.*
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
        onGetMoreClients = rememberIntent(viewModel) {
            GetClients
        },
        onClientClick = rememberIntentWithParam(viewModel) {
            ClientClicked(it)
        },
        modifier = modifier.fillMaxSize(),
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onGetMoreClients: () -> Unit,
    onClientClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        uiState.isInitialLoading -> HomeLoadingContent(
            modifier = modifier,
        )
        else -> HomeContent(
            isLoading = uiState.isLoading,
            clients = uiState.clients,
            onClientClick = onClientClick,
            isAllClientsFetched = uiState.isAllClientsFetched,
            onGetMoreClients = onGetMoreClients,
            modifier = modifier,
        )
    }
}

@Composable
private fun HandleEvents(events: Flow<HomeEvent>) {
    events.collectWithLifecycle {

    }
}