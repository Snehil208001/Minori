package com.snehil.minori.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * BaseViewModel implements state management (uiState) and single-shot events/actions (effect)
 * to facilitate clean architecture patterns inside Compose screens.
 *
 * @param State Represents the UI state of the screen (e.g. text inputs, loading status, credentials errors).
 * @param Effect Represents single-shot side-effects (e.g. navigation, triggers, system alerts, toast messages).
 */
abstract class BaseViewModel<State, Effect> : ViewModel() {

    /**
     * The initial state configuration of the screen. Must be implemented by the child class.
     */
    abstract val initialState: State

    private val _uiState by lazy { MutableStateFlow(initialState) }
    val uiState: StateFlow<State> by lazy { _uiState.asStateFlow() }

    private val _effect = MutableSharedFlow<Effect>()
    val effect: SharedFlow<Effect> = _effect.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    protected val currentState: State
        get() = _uiState.value

    /**
     * Atomically update the current UI state.
     */
    protected fun updateState(update: (State) -> State) {
        _uiState.value = update(_uiState.value)
    }

    /**
     * Dispatch a single-shot effect to the UI (e.g. navigate to home).
     */
    protected fun emitEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    /**
     * Helper to set loading indicator visibility.
     */
    protected fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    /**
     * Helper to set error message values.
     */
    protected fun setError(message: String?) {
        _errorMessage.value = message
    }
}
