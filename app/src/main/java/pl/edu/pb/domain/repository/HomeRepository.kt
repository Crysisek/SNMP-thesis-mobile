package pl.edu.pb.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.edu.pb.domain.model.Client

interface HomeRepository {
    fun getClients(page: Int): Flow<List<Client>>
}