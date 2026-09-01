package com.tayler.configproduct.ui.parameters

import androidx.lifecycle.viewModelScope
import com.tayler.core.model.ParamModel
import com.tayler.configproduct.domain.usecase.GetAppParamsUseCase
import com.tayler.configproduct.domain.usecase.UpdateAppParamsUseCase
import com.tayler.ui.di.IoDispatcher
import com.tayler.ui.ui.base.BaseViewModel
import com.tayler.ui.ui.base.GlobalUiStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParamViewModel @Inject constructor(
    private val getAppParamsUseCase: GetAppParamsUseCase,
    private val updateAppParamsUseCase: UpdateAppParamsUseCase,
    private val globalUiStateManager: GlobalUiStateManager,
    @IoDispatcher ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel(ioDispatcher) {

    private val _paramState = MutableStateFlow(ParamModel())
    val paramState: StateFlow<ParamModel> = _paramState.asStateFlow()

    fun loadParams(id: String) {
        execute(globalUiStateManager = globalUiStateManager) {
            val params = io { getAppParamsUseCase(id) }
            _paramState.value = params
        }
    }

    fun updateParams(param: ParamModel) {
        execute(globalUiStateManager = globalUiStateManager) {
            val updated = io { updateAppParamsUseCase(param) }
            _paramState.value = updated
        }
    }
}
