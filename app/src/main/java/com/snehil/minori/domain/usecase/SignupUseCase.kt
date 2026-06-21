package com.snehil.minori.domain.usecase

import com.snehil.minori.domain.model.User
import com.snehil.minori.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(fullName: String, email: String, password: String): Flow<Result<User>> {
        return authRepository.signup(fullName, email, password)
    }
}
