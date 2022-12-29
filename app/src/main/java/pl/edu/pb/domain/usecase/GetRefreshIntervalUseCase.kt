package pl.edu.pb.domain.usecase

import pl.edu.pb.common.extensions.resultOf
import pl.edu.pb.domain.repository.DataStoreRepository

fun interface GetRefreshIntervalUseCase : suspend (String) -> Result<Int>

suspend fun getRefreshInterval(
    key: String,
    dataStoreRepository: DataStoreRepository,
): Result<Int> = resultOf {
    dataStoreRepository.getInt(key)
}