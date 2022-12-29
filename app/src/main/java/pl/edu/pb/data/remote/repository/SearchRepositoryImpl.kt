package pl.edu.pb.data.remote.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.edu.pb.common.di.DispatcherIO
import pl.edu.pb.data.remote.api.ServerApi
import pl.edu.pb.data.remote.mapper.toDomainModel
import pl.edu.pb.domain.model.Client
import pl.edu.pb.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val serverApi: ServerApi,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher,
) : SearchRepository {

    override fun searchClients(searchUsername: String): Flow<List<Client>> = flow {
        val clients = serverApi
            .getAllClients()
            .toDomainModel()
            .clients
            .filter {
                it.username.contains(
                    searchUsername, true
                )
            }

        emit(clients)
    }.flowOn(dispatcherIO)
}