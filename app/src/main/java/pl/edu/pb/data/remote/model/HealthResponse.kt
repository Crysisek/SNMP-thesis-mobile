package pl.edu.pb.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HealthResponse(
    @SerialName("status")
    val status: String = "",

    @SerialName("components")
    val components: Components = Components(),
) {
    @Serializable
    data class Components(
        @SerialName("server")
        val server: ServerModel = ServerModel(),
    ) {
        @Serializable
        data class ServerModel(
            @SerialName("status")
            val status: String = "",

            @SerialName("details")
            val details: Details = Details()
        ) {
            @Serializable
            data class Details(
                @SerialName("Service")
                val service: String = "",
            )
        }
    }
}