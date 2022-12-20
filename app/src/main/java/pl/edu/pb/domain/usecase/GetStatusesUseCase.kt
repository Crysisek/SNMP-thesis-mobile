package pl.edu.pb.domain.usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import pl.edu.pb.common.extensions.resultOf
import pl.edu.pb.domain.model.StatusListPageInfo
import pl.edu.pb.domain.repository.StatusRepository
import java.io.IOException

fun interface GetStatusesUseCase : (String, Int, String, String) -> Flow<Result<StatusListPageInfo>>

fun getStatuses(
    clientId: String,
    page: Int,
    sortBy: String,
    sortDir: String,
    statusRepository: StatusRepository,
): Flow<Result<StatusListPageInfo>> = statusRepository
    .getStatuses(clientId, page, mapSortByName(sortBy), sortDir)
    .map {
        resultOf {
            it
        }
    }
    .retryWhen { cause, _ ->
        if (cause is IOException) {
            emit(Result.failure(cause))
            delay(RETRY_TIME_IN_MILLIS)

            true
        } else {
            false
        }
    }
    .catch {
        emit(Result.failure(it))
    }

private fun mapSortByName(sortBy: String) = if (sortBy != "receivingTime") "nameStatusPair.$sortBy" else sortBy

private const val RETRY_TIME_IN_MILLIS = 15_000L
