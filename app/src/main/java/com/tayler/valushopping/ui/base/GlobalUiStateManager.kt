package com.tayler.valushopping.ui.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

import androidx.compose.runtime.staticCompositionLocalOf

val LocalGlobalUiStateManager = staticCompositionLocalOf<GlobalUiStateManager> {
    error("No GlobalUiStateManager provided")
}

@Singleton
class GlobalUiStateManager @Inject constructor() {
    private val _uiState = MutableStateFlow(BaseUiState())
    val uiState: StateFlow<BaseUiState> = _uiState.asStateFlow()

    fun updateUiState(update: (BaseUiState) -> BaseUiState) {
        _uiState.update(update)
    }
}
