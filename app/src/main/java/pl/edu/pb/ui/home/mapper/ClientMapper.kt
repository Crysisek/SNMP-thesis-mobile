package pl.edu.pb.ui.home.mapper

import pl.edu.pb.domain.model.Client
import pl.edu.pb.ui.home.model.ClientDisplayable
import pl.edu.pb.ui.clientdetails.model.StatusDisplayable

fun Client.toPresentationModel() = ClientDisplayable(
    username = username,
    createdAt = TimeFormatter.formatter.format(createdAt),
    latestUpdateAt = TimeFormatter.formatter.format(latestUpdateAt),
    condition = condition,
    latestStatus = latestStatus?.toPresentationModel() ?: StatusDisplayable.empty(),
)
