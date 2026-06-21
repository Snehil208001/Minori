package com.snehil.minori.mainui.authentication.loginscreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val emailOrUsername: String = "",
    val password: String = "",
    val showFieldError: Boolean = false
)

sealed interface LoginEffect {
    object NavigateToHome : LoginEffect
    object NavigateToSignup : LoginEffect
    object NavigateToForgotPassword : LoginEffect
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginState, LoginEffect>() {

    override val initialState: LoginState = LoginState()

    fun onEmailOrUsernameChanged(value: String) {
        updateState { it.copy(emailOrUsername = value, showFieldError = false) }
    }

    fun onPasswordChanged(value: String) {
        updateState { it.copy(password = value, showFieldError = false) }
    }

    fun onLoginClicked() {
        val email = currentState.emailOrUsername
        val pass = currentState.password

        if (email.isBlank() || pass.isBlank()) {
            updateState { it.copy(showFieldError = true) }
            return
        }

        viewModelScope.launch {
            loginUseCase(email, pass)
                .onStart { setLoading(true); setError(null) }
                .catch { exception ->
                    setLoading(false)
                    setError(exception.message ?: "An unexpected error occurred")
                }
                .collect { result ->
                    setLoading(false)
                    result.fold(
                        onSuccess = {
                            emitEffect(LoginEffect.NavigateToHome)
                        },
                        onFailure = { exception ->
                            setError(exception.message ?: "Authentication failed")
                        }
                    )
                }
        }
    }

    fun onSignupClicked() {
        emitEffect(LoginEffect.NavigateToSignup)
    }

    fun onForgotPasswordClicked() {
        emitEffect(LoginEffect.NavigateToForgotPassword)
    }
}
