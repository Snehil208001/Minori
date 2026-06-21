package com.snehil.minori.domain.repository

import com.snehil.minori.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(emailOrUsername: String, password: String): Flow<Result<User>>
    fun signup(fullName: String, email: String, password: String): Flow<Result<User>>
    fun forgotPassword(email: String): Flow<Result<Unit>>
    fun getCurrentUser(): Flow<User?>
    fun logout(): Flow<Result<Unit>>
}
