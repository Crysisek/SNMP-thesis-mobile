package pl.edu.pb.domain.usecase

import pl.edu.pb.common.extensions.resultOf
import pl.edu.pb.domain.repository.DataStoreRepository

fun interface SaveCustomUsernameUseCase : suspend (String, String) -> Result<Unit>

suspend fun saveCustomUsername(
    key: String,
    value: String,
    dataStoreRepository: DataStoreRepository,
): Result<Unit> = resultOf {
    dataStoreRepository.putString(key, value)
}

