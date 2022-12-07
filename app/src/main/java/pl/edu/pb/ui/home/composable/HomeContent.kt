package pl.edu.pb.ui.home.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pl.edu.pb.R
import pl.edu.pb.ui.home.model.ClientDisplayable

@Composable
internal fun HomeContent(
    clients: List<ClientDisplayable>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .padding(12.dp),
    ) {
        items(clients) { client ->
            ClientItem(client)
        }
    }
}

@Composable
private fun ClientItem(
    client: ClientDisplayable,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 8.dp,
        modifier = modifier
            .fillMaxWidth(),
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
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = "ID - ${client.username}",
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text ="Created - ${client.createdAt}",
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = "Latest update - ${client.latestUpdateAt}",
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text ="Condition - ${client.condition}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}
