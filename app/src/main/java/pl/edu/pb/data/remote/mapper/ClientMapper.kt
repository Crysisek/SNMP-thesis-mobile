package pl.edu.pb.data.remote.mapper

import pl.edu.pb.data.remote.model.ClientPagedResponse
import pl.edu.pb.domain.model.Client
import java.time.Instant

fun ClientPagedResponse.ClientResponse.toDomainModel() = Client(
    username = username,
    createdAt = Instant.parse(createdAt),
    latestUpdateAt = Instant.parse(latestUpdateAt),
    condition = condition,
)
