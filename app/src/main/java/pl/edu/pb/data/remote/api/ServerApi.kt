package pl.edu.pb.data.remote.api

import pl.edu.pb.data.remote.model.ClientPagedResponse
import pl.edu.pb.data.remote.model.HealthResponse
import pl.edu.pb.data.remote.model.StatusesPagedResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerApi {

    @GET("actuator/health")
    suspend fun checkIfServerIsUp(): HealthResponse

    @GET("actuator")
    suspend fun loginToHomeScreen()

    @GET("/api/clients")
    suspend fun getClients(@Query("pageNo") page: Int): ClientPagedResponse

    @GET("/api/statuses/{client_id}")
    suspend fun getStatuses(
        @Path("client_id") clientId: String,
        @Query("pageNo") page: Int,
        @Query("sortBy") sortBy: String,
        @Query("sortDir") sortDir: String,
    ): StatusesPagedResponse
}