package com.snehil.minori.mainui.onboardingscreen.viewmodel

import com.snehil.minori.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class OnboardingState(
    val currentPage: Int = 0
)

sealed interface OnboardingEffect {
    object NavigateToLogin : OnboardingEffect
}

@HiltViewModel
class OnboardingViewModel @Inject constructor() : BaseViewModel<OnboardingState, OnboardingEffect>() {

    override val initialState: OnboardingState = OnboardingState()

    fun onPageChanged(page: Int) {
        updateState { it.copy(currentPage = page) }
    }

    fun onSkipClicked() {
        emitEffect(OnboardingEffect.NavigateToLogin)
    }

    fun onGetStartedClicked() {
        emitEffect(OnboardingEffect.NavigateToLogin)
    }

    fun onNextClicked(totalPages: Int) {
        val current = currentState.currentPage
        if (current < totalPages - 1) {
            updateState { it.copy(currentPage = current + 1) }
        } else {
            emitEffect(OnboardingEffect.NavigateToLogin)
        }
    }
}
