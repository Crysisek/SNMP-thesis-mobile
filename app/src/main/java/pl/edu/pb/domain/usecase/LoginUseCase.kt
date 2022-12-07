package pl.edu.pb.domain.usecase

import pl.edu.pb.common.extensions.resultOf
import pl.edu.pb.domain.repository.LoginRepository

fun interface LoginUseCase : suspend () -> Result<Unit>

suspend fun login(
    loginRepository: LoginRepository,
): Result<Unit> = resultOf {
    loginRepository.login()
}
