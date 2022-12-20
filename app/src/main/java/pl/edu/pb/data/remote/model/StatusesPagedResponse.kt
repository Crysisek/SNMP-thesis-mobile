package pl.edu.pb.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class StatusesPagedResponse(
    val statuses: List<StatusResponse>,
    val pageInfo: PageInfoResponse,
)