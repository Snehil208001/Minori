package com.snehil.minori.domain.usecase

import com.snehil.minori.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String): Flow<Result<Unit>> {
        return authRepository.forgotPassword(email)
    }
}
