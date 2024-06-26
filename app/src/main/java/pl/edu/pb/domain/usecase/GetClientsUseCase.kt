package pl.edu.pb.domain.usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import pl.edu.pb.common.extensions.resultOf
import pl.edu.pb.domain.model.Client
import pl.edu.pb.domain.model.ClientListPageInfo
import pl.edu.pb.domain.repository.HomeRepository
import java.io.IOException

fun interface GetClientsUseCase : (Int, Int) -> Flow<Result<ClientListPageInfo>>

fun getClients(
    page: Int,
    size: Int,
    homeRepository: HomeRepository,
): Flow<Result<ClientListPageInfo>> = homeRepository
    .getClients(page, size)
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
    .catch { // for other than IOException but it will stop collecting Flow
        emit(Result.failure(it)) // also catch does re-throw CancellationException
    }

private const val RETRY_TIME_IN_MILLIS = 10_000L
