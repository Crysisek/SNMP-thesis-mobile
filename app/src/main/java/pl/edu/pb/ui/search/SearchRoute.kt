package pl.edu.pb.ui.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.Flow
import pl.edu.pb.common.extensions.collectAsStateWithLifecycle
import pl.edu.pb.common.extensions.collectWithLifecycle

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel(),
) {
    HandleEvents(viewModel.event)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

}

@Composable
private fun HandleEvents(events: Flow<SearchEvent>) {
    events.collectWithLifecycle {
    }
}