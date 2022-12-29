package pl.edu.pb.ui.search.composable

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
import pl.edu.pb.ui.search.SearchEvent
import pl.edu.pb.ui.search.SearchIntent.*
import pl.edu.pb.ui.search.SearchUiState
import pl.edu.pb.ui.search.SearchViewModel

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    HandleEvents(viewModel.event)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchScreen(
        uiState = uiState,
        onClientClick = rememberIntentWithParam(viewModel) {
            ClientClicked(it)
        },
        onQueryChanged = rememberIntentWithParam(viewModel) {
            QueryChanged(it)
        },
        onSearchFocusChanged = rememberIntentWithParam(viewModel) {
            SearchFocusChanged(it)
        },
        onQueryCleared = rememberIntent(viewModel) {
            QueryCleared
        },
        modifier = modifier.fillMaxSize(),
    )
}

@Composable
private fun SearchScreen(
    uiState: SearchUiState,
    onClientClick: (String) -> Unit,
    onQueryChanged: (String) -> Unit,
    onSearchFocusChanged: (Boolean) -> Unit,
    onQueryCleared: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchContent(
        clients = uiState.clients,
        onClientClick = onClientClick,
        query = uiState.query,
        onQueryChanged = onQueryChanged,
        focused = uiState.isSearchBarFocused,
        onSearchFocusChanged = onSearchFocusChanged,
        searching = uiState.isSearching,
        onQueryCleared = onQueryCleared,
        modifier = modifier,
    )
}

@Composable
private fun HandleEvents(events: Flow<SearchEvent>) {
    events.collectWithLifecycle {
    }
}