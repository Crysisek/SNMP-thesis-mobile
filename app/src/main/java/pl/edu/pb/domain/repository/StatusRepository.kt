package pl.edu.pb.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.edu.pb.domain.model.StatusListPageInfo

interface StatusRepository {
    fun getStatuses(clientId: String, page: Int, sortBy: String, sortDir: String): Flow<StatusListPageInfo>
}