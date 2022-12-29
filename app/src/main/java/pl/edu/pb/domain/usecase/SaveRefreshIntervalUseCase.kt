package pl.edu.pb.domain.usecase

import pl.edu.pb.common.extensions.resultOf
import pl.edu.pb.domain.repository.DataStoreRepository

fun interface SaveRefreshIntervalUseCase : suspend (String, Int) -> Result<Unit>

suspend fun saveRefreshInterval(
    key: String,
    value: Int,
    dataStoreRepository: DataStoreRepository,
): Result<Unit> = resultOf {
    dataStoreRepository.putInt(key, value)
}

