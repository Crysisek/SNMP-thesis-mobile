package pl.edu.pb.ui.home.mapper

import pl.edu.pb.domain.model.Status
import pl.edu.pb.ui.clientdetails.model.StatusDisplayable

fun Status.toPresentationModel() = StatusDisplayable(
    receivingTime = TimeFormatter.formatter.format(receivingTime),
    nameStatusPair = nameStatusPair,
)