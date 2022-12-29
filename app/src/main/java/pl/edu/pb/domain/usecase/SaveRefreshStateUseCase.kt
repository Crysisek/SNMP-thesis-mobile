package pl.edu.pb.domain.usecase

import pl.edu.pb.common.extensions.resultOf
import pl.edu.pb.domain.repository.DataStoreRepository

fun interface SaveRefreshStateUseCase : suspend (String, Boolean) -> Result<Unit>

suspend fun saveRefreshState(
    key: String,
    value: Boolean,
    dataStoreRepository: DataStoreRepository,
): Result<Unit> = resultOf {
    dataStoreRepository.putBoolean(key, value)
}

