package pl.edu.pb.ui.clientdetails.composable

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pl.edu.pb.R
import pl.edu.pb.common.ui.composable.GenericPagedInfiniteList
import pl.edu.pb.common.ui.composable.RadioButtonWithText
import pl.edu.pb.common.ui.composable.bottomsheetscaffold.BottomSheetScaffold
import pl.edu.pb.common.ui.composable.bottomsheetscaffold.BottomSheetState
import pl.edu.pb.common.ui.composable.bottomsheetscaffold.BottomSheetValue
import pl.edu.pb.common.ui.composable.bottomsheetscaffold.rememberBottomSheetScaffoldState
import pl.edu.pb.common.ui.composable.bottomsheetscaffold.rememberBottomSheetState
import pl.edu.pb.ui.clientdetails.BottomSheetUiState
import pl.edu.pb.ui.clientdetails.model.StatusDisplayable

@Composable
internal fun ClientDetailsContent(
    isLoading: Boolean,
    isSortLoading: Boolean,
    statuses: Set<StatusDisplayable>,
    onStatusClick: () -> Unit,
    sortStatuses: () -> Unit,
    isAllStatusesFetched: Boolean,
    onGetMoreStatuses: () -> Unit,
    onSortByClick: (String) -> Unit,
    onSortDirClick: (BottomSheetUiState.SortDir) -> Unit,
    bottomSheetUiState: BottomSheetUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    val scope = rememberCoroutineScope()

    val onBackClicked = { backButtonLogic(bottomSheetState, onBackClick, scope) }

    BackHandler {
        onBackClicked()
    }

    LaunchedEffect(bottomSheetUiState) {
        if (bottomSheetState.isExpanded) {
            sortStatuses()
        }
    }

    BottomSheetScaffold(
        sheetContent = {
            BottomSheetSortingOptions(
                state = bottomSheetUiState,
                onSortByClick = onSortByClick,
                onSortDirClick = onSortDirClick,
            )
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        modifier = modifier,
    ) {
        if (isSortLoading) {
            ClientDetailsLoadingContent(
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box {
                GenericPagedInfiniteList(
                    items = statuses.toList(),
                    isLoadingMoreContent = isLoading,
                    isEverythingFetched = isAllStatusesFetched,
                    onGetMore = onGetMoreStatuses,
                    showJumpToStartFab = true,
                    indexWhenFabWillBeShown = 10,
                    fabPaddingValues = PaddingValues(bottom = 88.dp, end = 8.dp),
                ) { status ->
                    StatusItem(
                        status = status,
                        onStatusClick = { onStatusClick() },
                    )
                }
                FloatingActionButton(
                    onClick = { scope.launch { bottomSheetState.expand() } },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 22.dp, end = 8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(id = R.string.fab_filter_text)
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusItem(
    status: StatusDisplayable,
    onStatusClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 8.dp,
        modifier = modifier
            .clickable { onStatusClick() }
            .fillMaxWidth(),
    ) {
        StatusInfo(status = status)
    }
}

@Composable
private fun BottomSheetSortingOptions(
    state: BottomSheetUiState,
    onSortByClick: (String) -> Unit,
    onSortDirClick: (BottomSheetUiState.SortDir) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .clip(ShapeDefaults.ExtraLarge)
                .align(Alignment.Center)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(8.dp),
        ) {
            SortDirectionOptions(
                sortDir = state.sortDir,
                onSortDirClick = onSortDirClick,
            )
            Spacer(modifier = Modifier.height(8.dp))
            SortByOptions(
                sortBy = state.sortBy,
                sortByOptions = state.sortByOption,
                onSortByClick = onSortByClick
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun SortDirectionOptions(
    sortDir: BottomSheetUiState.SortDir,
    onSortDirClick: (BottomSheetUiState.SortDir) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.sort_dir),
            modifier = Modifier
                .padding(start = 10.dp),
        )
        Row {
            BottomSheetUiState.SortDir.values().forEach {
                RadioButtonWithText(
                    text = it.name,
                    isSelected = sortDir == it,
                    onRadioButtonClicked = { onSortDirClick(it) },
                )
            }
        }
    }
}

@Composable
private fun SortByOptions(
    sortBy: String,
    sortByOptions: Set<String>,
    onSortByClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.sort_by),
            modifier = Modifier
                .padding(start = 10.dp),
        )
        sortByOptions.forEach {
            RadioButtonWithText(
                text = it,
                isSelected = sortBy == it,
                onRadioButtonClicked = { onSortByClick(it) },
            )
        }
    }
}

private fun backButtonLogic(
    bottomSheetState: BottomSheetState,
    onBackClick: () -> Unit,
    scope: CoroutineScope,
) {
    when {
        bottomSheetState.isExpanded -> scope.launch { bottomSheetState.collapse() }
        else -> onBackClick()
    }
}
