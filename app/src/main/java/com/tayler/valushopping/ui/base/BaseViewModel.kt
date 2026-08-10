package com.tayler.valushopping.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel(private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {

    private val _uiStateBase = MutableStateFlow(BaseUiState())
    val uiStateBase: StateFlow<BaseUiState> = _uiStateBase.asStateFlow()

    fun updateUiState(update: (BaseUiState) -> BaseUiState) {
        _uiStateBase.update(update)
    }

    fun execute(
        loading: Boolean = true,
        globalUiStateManager: GlobalUiStateManager? = null,
        func: suspend BaseViewModel.() -> Unit
    ) {
        viewModelScope.launch {
            try {
                updateUiState { currentState ->
                    currentState.copy(loading = loading, shimmer = true, error = false)
                }
                globalUiStateManager?.updateUiState { currentState ->
                    currentState.copy(loading = loading, shimmer = true, error = false)
                }
                func()
            } catch (ex: Exception) {
                if (ex is CancellationException) throw ex
                ex.printStackTrace()
                updateUiState { currentState ->
                    currentState.copy(
                        error = true,
                        errorType = ex
                    )
                }
                globalUiStateManager?.updateUiState { currentState ->
                    currentState.copy(
                        error = true,
                        errorType = ex
                    )
                }
            } finally {
                updateUiState { currentState ->
                    currentState.copy(loading = false, shimmer = false)
                }
                globalUiStateManager?.updateUiState { currentState ->
                    currentState.copy(loading = false, shimmer = false)
                }
            }
        }
    }

    fun <T> executeState(
        stateFlow: MutableStateFlow<T?>,
        globalUiStateManager: GlobalUiStateManager? = null,
        func: suspend () -> T
    ) {
        execute(loading = false, globalUiStateManager = globalUiStateManager) {
            val result = io { func() }
            stateFlow.value = result
        }
    }

    protected suspend fun <T> io(block: suspend () -> T): T = withContext(ioDispatcher) {// NOSONAR
        block()
    }
}
