package com.snehil.minori.data.repository

import com.snehil.minori.domain.model.User
import com.snehil.minori.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    override fun login(emailOrUsername: String, password: String): Flow<Result<User>> = flow {
        // Mock authentication delay
        delay(1500)
        if (password == "error" || emailOrUsername == "error") {
            emit(Result.failure(Exception("Invalid credentials. Try any password except 'error'.")))
        } else {
            emit(Result.success(User(uid = "12345", name = "Snehil", email = if (emailOrUsername.contains("@")) emailOrUsername else "snehil@example.com")))
        }
    }

    override fun signup(fullName: String, email: String, password: String): Flow<Result<User>> = flow {
        delay(1500)
        if (email == "error@error.com") {
            emit(Result.failure(Exception("Email already registered.")))
        } else {
            emit(Result.success(User(uid = "12345", name = fullName, email = email)))
        }
    }

    override fun forgotPassword(email: String): Flow<Result<Unit>> = flow {
        delay(1500)
        if (email == "error@error.com") {
            emit(Result.failure(Exception("Email not found.")))
        } else {
            emit(Result.success(Unit))
        }
    }

    override fun getCurrentUser(): Flow<User?> = flow {
        emit(null)
    }

    override fun logout(): Flow<Result<Unit>> = flow {
        emit(Result.success(Unit))
    }
}
