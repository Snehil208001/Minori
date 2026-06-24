package com.snehil.minori.mainui.settingsscreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.snehil.minori.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsState(
    val isDarkMode: Boolean = false,
    val isNotificationsEnabled: Boolean = true,
    val selectedLanguage: String = "English",
    val selectedCurrency: String = "INR (₹)",
    val appVersion: String = "1.0.0 (Beta)",
    val isLogoutDialogVisible: Boolean = false
)

sealed interface SettingsEffect {
    object NavigateBack : SettingsEffect
    object NavigateToProfile : SettingsEffect
    object NavigateToLogin : SettingsEffect
    data class ShowToast(val message: String) : SettingsEffect
}

@HiltViewModel
class SettingsViewModel @Inject constructor() : BaseViewModel<SettingsState, SettingsEffect>() {

    override val initialState: SettingsState = SettingsState()

    fun toggleDarkMode(enabled: Boolean) {
        updateState { it.copy(isDarkMode = enabled) }
        emitEffect(SettingsEffect.ShowToast("Theme theme preferences updated!"))
    }

    fun toggleNotifications(enabled: Boolean) {
        updateState { it.copy(isNotificationsEnabled = enabled) }
        emitEffect(SettingsEffect.ShowToast(if (enabled) "Notifications enabled" else "Notifications muted"))
    }

    fun updateLanguage(language: String) {
        updateState { it.copy(selectedLanguage = language) }
    }

    fun updateCurrency(currency: String) {
        updateState { it.copy(selectedCurrency = currency) }
    }

    fun showLogoutDialog() {
        updateState { it.copy(isLogoutDialogVisible = true) }
    }

    fun hideLogoutDialog() {
        updateState { it.copy(isLogoutDialogVisible = false) }
    }

    fun logout() {
        updateState { it.copy(isLogoutDialogVisible = false) }
        emitEffect(SettingsEffect.NavigateToLogin)
    }

    fun goToProfile() {
        emitEffect(SettingsEffect.NavigateToProfile)
    }

    fun goBack() {
        emitEffect(SettingsEffect.NavigateBack)
    }
}
