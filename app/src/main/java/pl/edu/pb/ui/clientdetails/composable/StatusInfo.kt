package pl.edu.pb.ui.clientdetails.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.edu.pb.ui.clientdetails.model.StatusDisplayable

@Composable
fun StatusInfo(
    status: StatusDisplayable,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
            .padding(
                start = 10.dp,
                end = 10.dp,
                bottom = 10.dp
            ),
    ) {
        for (entry in status.nameStatusPair.entries) {
           StatusRow(
               name = entry.key,
               value = entry.value,
           )
        }
        StatusRow(
            name = "receivingTime",
            value = status.receivingTime,
        )
    }
}

@Composable
fun StatusRow(name: String, value: String) {
    Row {
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.alignByBaseline(),
        )
        Text(
            text = " - $value",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.alignByBaseline(),
        )
    }
}
