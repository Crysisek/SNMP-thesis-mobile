package pl.edu.pb.data.remote.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.edu.pb.common.di.DispatcherIO
import pl.edu.pb.data.remote.api.ServerApi
import pl.edu.pb.data.remote.mapper.toDomainModel
import pl.edu.pb.domain.model.Client
import pl.edu.pb.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val serverApi: ServerApi,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher,
) : HomeRepository {

    override fun getClients(page: Int): Flow<List<Client>> = flow {
        val clients = serverApi
            .getClients(page)
            .clients
            .filter { it.role == ROLE }
            .map { it.toDomainModel() }

        emit(clients)
    }.flowOn(dispatcherIO)

    private companion object {
        private const val ROLE = "USER"
    }
}