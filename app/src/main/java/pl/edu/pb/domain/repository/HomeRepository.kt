package pl.edu.pb.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.edu.pb.domain.model.Client
import pl.edu.pb.domain.model.ClientListPageInfo

interface HomeRepository {
    fun getClients(page: Int): Flow<ClientListPageInfo>
}