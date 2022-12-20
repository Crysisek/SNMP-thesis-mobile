package pl.edu.pb.data.remote.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.edu.pb.common.di.DispatcherIO
import pl.edu.pb.data.remote.api.ServerApi
import pl.edu.pb.data.remote.mapper.toDomainModel
import pl.edu.pb.domain.model.StatusListPageInfo
import pl.edu.pb.domain.repository.StatusRepository
import javax.inject.Inject

class StatusRepositoryImpl @Inject constructor(
    private val serverApi: ServerApi,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher,
) : StatusRepository {

    override fun getStatuses(clientId: String, page: Int, sortBy: String, sortDir: String): Flow<StatusListPageInfo> = flow {
        val statusListPaged = serverApi
            .getStatuses(clientId, page, sortBy, sortDir)
            .toDomainModel()

        emit(statusListPaged)
    }.flowOn(dispatcherIO)
}