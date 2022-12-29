package pl.edu.pb.domain.usecase

import pl.edu.pb.common.extensions.resultOf
import pl.edu.pb.domain.repository.DataStoreRepository

fun interface GetRefreshStateUseCase : suspend (String) -> Result<Boolean>

suspend fun getRefreshState(
    key: String,
    dataStoreRepository: DataStoreRepository,
): Result<Boolean> = resultOf {
    dataStoreRepository.getBoolean(key)
}