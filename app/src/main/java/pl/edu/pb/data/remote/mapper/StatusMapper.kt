package pl.edu.pb.data.remote.mapper

import pl.edu.pb.data.remote.model.StatusResponse
import pl.edu.pb.data.remote.model.StatusesPagedResponse
import pl.edu.pb.domain.model.Status
import pl.edu.pb.domain.model.StatusListPageInfo
import java.time.Instant

fun StatusesPagedResponse.toDomainModel() = StatusListPageInfo(
    statuses = statuses.map { it.toDomainModel() },
    currentPage = pageInfo.currentPage,
    totalPages = pageInfo.totalPages,
)

fun StatusResponse.toDomainModel() = Status(
    receivingTime = Instant.parse(receivingTime),
    nameStatusPair = nameStatusPair,
)
