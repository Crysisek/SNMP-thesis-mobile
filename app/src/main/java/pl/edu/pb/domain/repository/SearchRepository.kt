package pl.edu.pb.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.edu.pb.domain.model.Client

interface SearchRepository {
    fun searchClients(searchUsername: String): Flow<List<Client>>
}