package pl.edu.pb.data.remote.repository

import pl.edu.pb.data.remote.api.ServerApi
import pl.edu.pb.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val serverApi: ServerApi,
) : LoginRepository {

    override suspend fun checkIfServerIsUp() = serverApi
        .checkIfServerIsUp()
        .let {
            it.status == UP && it.components.server.status == UP && it.components.server.details.service == SERVER_NAME
        }

    override suspend fun login() = serverApi
        .loginToHomeScreen()

    private companion object {
        private const val UP = "UP"
        private const val SERVER_NAME = "SNMP-server"
    }
}