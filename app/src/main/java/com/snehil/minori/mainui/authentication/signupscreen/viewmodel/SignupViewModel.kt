package com.snehil.minori.mainui.authentication.signupscreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.domain.usecase.SignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignupState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorText: String? = null
)

sealed interface SignupEffect {
    object NavigateToHome : SignupEffect
    object NavigateToLogin : SignupEffect
}

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase
) : BaseViewModel<SignupState, SignupEffect>() {

    override val initialState: SignupState = SignupState()

    fun onFullNameChanged(value: String) {
        updateState { it.copy(fullName = value, errorText = null) }
    }

    fun onEmailChanged(value: String) {
        updateState { it.copy(email = value, errorText = null) }
    }

    fun onPasswordChanged(value: String) {
        updateState { it.copy(password = value, errorText = null) }
    }

    fun onConfirmPasswordChanged(value: String) {
        updateState { it.copy(confirmPassword = value, errorText = null) }
    }

    fun onSignupClicked() {
        val name = currentState.fullName
        val mail = currentState.email
        val pass = currentState.password
        val confPass = currentState.confirmPassword

        if (name.isBlank() || mail.isBlank() || pass.isBlank() || confPass.isBlank()) {
            updateState { it.copy(errorText = "Please fill in all fields.") }
            return
        }

        if (pass != confPass) {
            updateState { it.copy(errorText = "Passwords do not match.") }
            return
        }

        viewModelScope.launch {
            signupUseCase(name, mail, pass)
                .onStart { setLoading(true); updateState { it.copy(errorText = null) } }
                .catch { exception ->
                    setLoading(false)
                    updateState { it.copy(errorText = exception.message ?: "An unexpected error occurred") }
                }
                .collect { result ->
                    setLoading(false)
                    result.fold(
                        onSuccess = {
                            emitEffect(SignupEffect.NavigateToHome)
                        },
                        onFailure = { exception ->
                            updateState { it.copy(errorText = exception.message ?: "Signup failed") }
                        }
                    )
                }
        }
    }

    fun onLoginClicked() {
        emitEffect(SignupEffect.NavigateToLogin)
    }
}
