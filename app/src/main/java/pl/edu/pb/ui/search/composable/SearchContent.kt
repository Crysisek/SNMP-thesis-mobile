package pl.edu.pb.ui.search.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.edu.pb.common.ui.Dimens
import pl.edu.pb.common.ui.composable.ClientItem
import pl.edu.pb.ui.home.model.ClientDisplayable

@Composable
internal fun SearchContent(
    clients: Set<ClientDisplayable>,
    onClientClick: (String) -> Unit,
    query: String,
    onQueryChanged: (String) -> Unit,
    focused: Boolean,
    onSearchFocusChanged: (Boolean) -> Unit,
    searching: Boolean,
    onQueryCleared: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(Dimens.betweenListItems),
        contentPadding = PaddingValues(Dimens.contentPadding),
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            SearchBar(
                query = query,
                onQueryChange = onQueryChanged,
                searchFocused = focused,
                onSearchFocusChange = onSearchFocusChanged,
                onClearQuery = onQueryCleared,
                searching = searching,
            )
        }
        items(clients.toList()) {
            ClientItem(
                client = it,
                onClientClick = { onClientClick(it.username) },
            )
        }
    }
}