package pl.edu.pb.ui.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.Flow
import pl.edu.pb.common.extensions.collectAsStateWithLifecycle
import pl.edu.pb.common.extensions.collectWithLifecycle

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    HandleEvents(viewModel.event)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchScreen(
        uiState = uiState,
        modifier = modifier.fillMaxSize(),
    )
}

@Composable
private fun SearchScreen(
    uiState: SearchUiState,
    modifier: Modifier = Modifier,
) {
    when {
       // uiState.isI
    }
}

@Composable
private fun HandleEvents(events: Flow<SearchEvent>) {
    events.collectWithLifecycle {
    }
}