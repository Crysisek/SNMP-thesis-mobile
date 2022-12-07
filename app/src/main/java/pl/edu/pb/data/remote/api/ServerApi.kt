package pl.edu.pb.data.remote.api

import pl.edu.pb.data.remote.model.ClientPagedResponse
import pl.edu.pb.data.remote.model.HealthResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApi {

    @GET("actuator/health")
    suspend fun checkIfServerIsUp(): HealthResponse

    @GET("actuator")
    suspend fun loginToHomeScreen()

    @GET("/api/clients")
    suspend fun getClients(@Query("pageNo") page: Int): ClientPagedResponse
}