package pl.edu.pb.domain.usecase

import pl.edu.pb.common.extensions.resultOf
import pl.edu.pb.domain.repository.LoginRepository

fun interface CheckServerUpUseCase : suspend () -> Result<Boolean>

suspend fun checkIfServerIsUp(
    loginRepository: LoginRepository,
): Result<Boolean> = resultOf {
    loginRepository.checkIfServerIsUp()
}
