package com.snehil.minori.domain.usecase

import com.snehil.minori.domain.model.User
import com.snehil.minori.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(emailOrUsername: String, password: String): Flow<Result<User>> {
        return authRepository.login(emailOrUsername, password)
    }
}
