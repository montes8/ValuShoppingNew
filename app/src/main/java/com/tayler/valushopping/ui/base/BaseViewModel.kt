package com.tayler.valushopping.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel(private var valeD: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {

    companion object {
        private val _sharedUiStateBase = MutableStateFlow(BaseUiState())
        val sharedUiStateBase: StateFlow<BaseUiState> = _sharedUiStateBase.asStateFlow()

        fun updateSharedUiState(update: (BaseUiState) -> BaseUiState) {
            _sharedUiStateBase.update(update)
        }
    }
    val uiStateBase: StateFlow<BaseUiState> = _sharedUiStateBase

    fun execute(loading: Boolean = true, func: suspend BaseViewModel.() -> Unit) {
        viewModelScope.launch {
            try {
                if (loading) {
                    updateSharedUiState { currentState ->
                        currentState.copy(loading = true, error = false)
                    }
                }
                func()
            } catch (ex: Exception) {
                ex.printStackTrace()
                updateSharedUiState { currentState ->
                    currentState.copy(
                        error = true,
                        errorType = ex
                    )
                }
            } finally {
                if (loading) {
                    updateSharedUiState { currentState ->
                        currentState.copy(loading = false)
                    }
                }
            }
        }
    }

    suspend fun <T> io(block: suspend () -> T): T = withContext(Dispatchers.IO) {
        block()
    }

    suspend fun <T> default(block: suspend () -> T): T = withContext(Dispatchers.Default) {
        block()
    }

    fun updateUiState(update: (BaseUiState) -> BaseUiState) {
        updateSharedUiState(update)
    }
}