package pl.edu.pb.domain.usecase

import pl.edu.pb.common.extensions.resultOf
import pl.edu.pb.domain.repository.DataStoreRepository

fun interface GetCustomUsernameUseCase : suspend (String) -> Result<String>

suspend fun getCustomUsername(
    key: String,
    dataStoreRepository: DataStoreRepository,
): Result<String> = resultOf {
    dataStoreRepository.getString(key)
}