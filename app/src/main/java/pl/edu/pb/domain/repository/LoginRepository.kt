package pl.edu.pb.domain.repository

interface LoginRepository {
    suspend fun checkIfServerIsUp(): Boolean
    suspend fun login()
}