package pl.edu.pb.ui.home.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.edu.pb.common.ui.composable.ClientItem
import pl.edu.pb.common.ui.composable.GenericPagedInfiniteList
import pl.edu.pb.ui.home.model.ClientDisplayable

@Composable
internal fun HomeContent(
    isLoading: Boolean,
    clients: List<ClientDisplayable>,
    onClientClick: (String) -> Unit,
    isAllClientsFetched: Boolean,
    onGetMoreClients: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GenericPagedInfiniteList(
        items = clients,
        isLoadingMoreContent = isLoading,
        isEverythingFetched = isAllClientsFetched,
        onGetMore = onGetMoreClients,
        showJumpToStartFab = true,
        fabPaddingValues = PaddingValues(bottom = 18.dp, end = 8.dp),
        indexWhenFabWillBeShown = 15,
        modifier = modifier,
    ) { client ->
        ClientItem(
            client = client,
            onClientClick = { onClientClick(client.username) },
        )
    }
}
