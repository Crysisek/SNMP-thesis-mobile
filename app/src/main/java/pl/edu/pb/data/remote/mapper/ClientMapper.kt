package pl.edu.pb.data.remote.mapper

import pl.edu.pb.data.remote.model.ClientPagedResponse
import pl.edu.pb.data.remote.model.StatusResponse
import pl.edu.pb.domain.model.Client
import pl.edu.pb.domain.model.ClientListPageInfo
import pl.edu.pb.domain.model.Status
import java.time.Instant

fun ClientPagedResponse.toDomainModel() = ClientListPageInfo(
    clients = clients
        .filter { it.role == ROLE }
        .map { it.toDomainModel() },
    currentPage = pageInfo.currentPage,
    totalPages = pageInfo.totalPages,
)

private fun ClientPagedResponse.ClientResponse.toDomainModel() = Client(
    username = username,
    createdAt = Instant.parse(createdAt),
    latestUpdateAt = Instant.parse(latestUpdateAt),
    condition = condition,
    latestStatus = latestStatuses.maxByOrNull { it.receivingTime }?.toDomainModel()
)

private const val ROLE = "USER"