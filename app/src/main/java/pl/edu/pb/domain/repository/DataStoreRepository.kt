package pl.edu.pb.domain.repository

interface DataStoreRepository {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String
}