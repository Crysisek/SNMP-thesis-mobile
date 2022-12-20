package pl.edu.pb.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusResponse(
    @SerialName("receivingTime")
    val receivingTime: String,
    @SerialName("nameStatusPair")
    val nameStatusPair: Map<String, String>,
)