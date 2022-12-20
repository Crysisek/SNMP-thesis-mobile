package pl.edu.pb.ui.clientdetails.composable

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
import pl.edu.pb.ui.clientdetails.BottomSheetUiState
import pl.edu.pb.ui.clientdetails.ClientDetailsEvent
import pl.edu.pb.ui.clientdetails.ClientDetailsIntent
import pl.edu.pb.ui.clientdetails.ClientDetailsIntent.BackClicked
import pl.edu.pb.ui.clientdetails.ClientDetailsIntent.GetStatuses
import pl.edu.pb.ui.clientdetails.ClientDetailsIntent.SortByClicked
import pl.edu.pb.ui.clientdetails.ClientDetailsIntent.SortStatuses
import pl.edu.pb.ui.clientdetails.ClientDetailsIntent.StatusClicked
import pl.edu.pb.ui.clientdetails.ClientDetailsUiState
import pl.edu.pb.ui.clientdetails.ClientDetailsViewModel

@Composable
fun ClientDetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: ClientDetailsViewModel = hiltViewModel(),
) {
    HandleEvents(viewModel.event)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ClientDetailsScreen(
        uiState = uiState,
        onGetMoreStatuses = rememberIntent(viewModel) {
            GetStatuses
        },
        onStatusClick = rememberIntent(viewModel) {
            StatusClicked
        },
        onSortByClick = rememberIntentWithParam(viewModel) {
            SortByClicked(it)
        },
        sortStatuses = rememberIntent(viewModel) {
            SortStatuses
        },
        onSortDirClick = rememberIntentWithParam(viewModel) {
            ClientDetailsIntent.SortDirClicked(it)
        },
        onBackClick = rememberIntent(viewModel) {
            BackClicked
        },
        modifier = modifier
            .fillMaxSize(),
    )
}

@Composable
private fun ClientDetailsScreen(
    uiState: ClientDetailsUiState,
    onGetMoreStatuses: () -> Unit,
    onStatusClick: () -> Unit,
    sortStatuses: () -> Unit,
    onSortByClick: (String) -> Unit,
    onSortDirClick: (BottomSheetUiState.SortDir) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        uiState.isInitialLoading -> ClientDetailsLoadingContent(
            modifier = modifier,
        )
        else -> ClientDetailsContent(
            isLoading = uiState.isLoading,
            isSortLoading = uiState.isSortLoading,
            statuses = uiState.statuses,
            onStatusClick = onStatusClick,
            sortStatuses = sortStatuses,
            isAllStatusesFetched = uiState.isAllStatusesFetched,
            onGetMoreStatuses = onGetMoreStatuses,
            onSortByClick = onSortByClick,
            onSortDirClick = onSortDirClick,
            bottomSheetUiState = uiState.bottomSheetUiState,
            onBackClick = onBackClick,
            modifier = modifier,
        )
    }
}

@Composable
private fun HandleEvents(events: Flow<ClientDetailsEvent>) {
    events.collectWithLifecycle {

    }
}
