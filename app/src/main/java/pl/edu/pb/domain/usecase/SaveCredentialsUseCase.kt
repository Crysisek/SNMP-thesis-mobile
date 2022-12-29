package pl.edu.pb.domain.usecase

import pl.edu.pb.common.extensions.resultOf
import pl.edu.pb.domain.repository.DataStoreRepository

fun interface SaveCredentialsUseCase : suspend (List<String>, List<String>) -> Result<Unit>

suspend fun saveCredentials(
    keys: List<String>,
    values: List<String>,
    dataStoreRepository: DataStoreRepository,
): Result<Unit> = resultOf {
    keys.zip(values).forEach { pair ->
        dataStoreRepository.putString(pair.first, pair.second)
    }
}
