package pl.edu.pb.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClientPagedResponse(
    val clients: List<ClientResponse>,
    val pageInfo: PageInfoResponse,
) {
    @Serializable
    data class ClientResponse(
        @SerialName("username")
        val username: String,
        @SerialName("createdAt")
        val createdAt: String,
        @SerialName("latestUpdateAt")
        val latestUpdateAt: String,
        @SerialName("condition")
        val condition: String,
        @SerialName("role")
        val role: String,
        @SerialName("statusList")
        val newestStatuses: List<StatusResponse>,
    ) {
        @Serializable
        data class StatusResponse(
            @SerialName("receivingTime")
            val receivingTime: String,
            @SerialName("nameStatusPair")
            val nameStatusPair: Map<String, String>,
        )
    }
}