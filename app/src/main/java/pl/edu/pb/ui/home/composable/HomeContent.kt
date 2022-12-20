package pl.edu.pb.ui.home.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.edu.pb.R
import pl.edu.pb.common.ui.composable.GenericPagedInfiniteList
import pl.edu.pb.ui.clientdetails.composable.StatusInfo
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

@Composable
private fun ClientItem(
    client: ClientDisplayable,
    onClientClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 8.dp,
        modifier = modifier
            .clickable { onClientClick() }
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_computer),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(76.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = CircleShape,
                        ),
                )
                ClientInfo(
                    client = client,
                    expanded = expanded,
                    expand = { expanded = !expanded }
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                StatusInfo(
                    status = client.latestStatus,
                )
            }
        }
    }
}

@Composable
private fun ClientInfo(
    client: ClientDisplayable,
    expanded: Boolean,
    expand: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(start = 10.dp),
    ) {
        Text(
            text = client.username,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(bottom = 10.dp),
        )
        Row {
            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                Text(
                    text = "Created - ${client.createdAt}",
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = "Latest update - ${client.latestUpdateAt}",
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = "Condition - ${client.condition}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            val degrees by animateFloatAsState(
                if (expanded) -180f else 0f
            )
            IconButton(
                onClick = { expand() }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = modifier
                        .rotate(degrees)
                )
            }
        }
    }
}
