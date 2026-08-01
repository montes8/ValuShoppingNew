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

    private val _uiStateBase = MutableStateFlow(BaseUiState())
    val uiStateBase: StateFlow<BaseUiState> = _uiStateBase.asStateFlow()

    fun execute(loading: Boolean = true, func: suspend BaseViewModel.() -> Unit) {
        viewModelScope.launch {
            try {
                if (loading) {
                    _uiStateBase.update { currentState ->
                        currentState.copy(loading = true, error = false)
                    }
                }
                func()

            } catch (ex: Exception) {
                ex.printStackTrace()
                _uiStateBase.update { currentState ->
                    currentState.copy(
                        error = true,
                        errorType = ex
                    )
                }
            } finally {
                if (loading) {
                    _uiStateBase.update { currentState ->
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

    fun dismissErrorDialog(dialogResult: Boolean) {
        _uiStateBase.update { currentState ->
            currentState.copy(
                error = false,
                popUpGeneric = true,
                popUpGenericValue = dialogResult
            )
        }
    }

}