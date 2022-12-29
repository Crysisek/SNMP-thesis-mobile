package pl.edu.pb.common.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import pl.edu.pb.R
import pl.edu.pb.common.ui.Dimens

@Composable
fun <T> GenericPagedInfiniteList(
    items: List<T>,
    isLoadingMoreContent: Boolean,
    isEverythingFetched: Boolean,
    onGetMore: () -> Unit,
    modifier: Modifier = Modifier,
    showJumpToStartFab: Boolean = false,
    indexWhenFabWillBeShown: Int = 0,
    fabPaddingValues: PaddingValues = PaddingValues(0.dp),
    content: @Composable (T) -> Unit,
) {
    val listState = rememberLazyListState()
    val isScrollToEnd by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }
    LaunchedEffect(isScrollToEnd) {
        if (!isEverythingFetched && isScrollToEnd && !isLoadingMoreContent) {
            onGetMore()
        }
    }
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier,
    ) {
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(Dimens.betweenListItems),
            contentPadding = PaddingValues(Dimens.contentPadding),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(items) { item ->
                content(item)
            }
        }
        if (showJumpToStartFab) {
            val isJumpToStartVisible by remember { derivedStateOf { listState.firstVisibleItemIndex > indexWhenFabWillBeShown } }
            val scope = rememberCoroutineScope()
            AnimatedVisibility(
                visible = isJumpToStartVisible,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(fabPaddingValues)
                        .navigationBarsPadding(),
                    onClick = { scope.launch { listState.animateScrollToItem(0) } },
                ) {
                    Icon(
                        Icons.Filled.KeyboardArrowUp,
                        contentDescription = stringResource(id = R.string.fab_jump_up),
                    )
                }
            }
        }
        BottomLoadingSpinner(isLoading = isLoadingMoreContent)
    }
}