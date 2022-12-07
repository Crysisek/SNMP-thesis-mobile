package pl.edu.pb.domain.usecase

import pl.edu.pb.common.extensions.resultOf
import pl.edu.pb.domain.repository.DataStoreRepository

fun interface GetCredentialsUseCase : suspend (List<String>) -> Result<List<String>>

suspend fun getCredentials(
    keys: List<String>,
    dataStoreRepository: DataStoreRepository,
): Result<List<String>> = resultOf {
    keys.map {
        dataStoreRepository.getString(it)
    }
}