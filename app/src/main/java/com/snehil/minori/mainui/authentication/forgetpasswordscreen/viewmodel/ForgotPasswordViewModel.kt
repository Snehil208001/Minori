package com.snehil.minori.mainui.authentication.forgetpasswordscreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.domain.usecase.ForgotPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ForgotPasswordState(
    val email: String = "",
    val errorText: String? = null,
    val successText: String? = null
)

sealed interface ForgotPasswordEffect {
    object NavigateToLogin : ForgotPasswordEffect
}

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : BaseViewModel<ForgotPasswordState, ForgotPasswordEffect>() {

    override val initialState: ForgotPasswordState = ForgotPasswordState()

    fun onEmailChanged(value: String) {
        updateState { it.copy(email = value, errorText = null, successText = null) }
    }

    fun onSubmitClicked() {
        val mail = currentState.email

        if (mail.isBlank()) {
            updateState { it.copy(errorText = "Please enter your email address.") }
            return
        }

        viewModelScope.launch {
            forgotPasswordUseCase(mail)
                .onStart { setLoading(true); updateState { it.copy(errorText = null, successText = null) } }
                .catch { exception ->
                    setLoading(false)
                    updateState { it.copy(errorText = exception.message ?: "An unexpected error occurred") }
                }
                .collect { result ->
                    setLoading(false)
                    result.fold(
                        onSuccess = {
                            updateState { it.copy(successText = "A password reset link has been sent to your email.") }
                        },
                        onFailure = { exception ->
                            updateState { it.copy(errorText = exception.message ?: "Failed to request password reset") }
                        }
                    )
                }
        }
    }

    fun onBackClicked() {
        emitEffect(ForgotPasswordEffect.NavigateToLogin)
    }
}
