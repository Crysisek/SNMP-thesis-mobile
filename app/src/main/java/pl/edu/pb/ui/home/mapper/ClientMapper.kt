package pl.edu.pb.ui.home.mapper

import pl.edu.pb.domain.model.Client
import pl.edu.pb.ui.home.model.ClientDisplayable
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Client.toPresentationModel() = ClientDisplayable(
    username = username,
    createdAt = formatter.format(createdAt),
    latestUpdateAt = formatter.format(latestUpdateAt),
    condition = condition,
)

private const val PATTERN_FORMAT = "dd/MM/yyyy-HH:mm:ss"

private val formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(ZoneId.systemDefault())
