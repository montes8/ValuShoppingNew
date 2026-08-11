package com.tayler.valushopping.ui.detail

import com.tayler.entity.ImageMoreModel
import com.tayler.usecases.DataUseCase
import com.tayler.valushopping.di.IoDispatcher
import com.tayler.valushopping.ui.base.BaseViewModel
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dataUseCase: DataUseCase,
    private val globalUiStateManager: GlobalUiStateManager,
    @IoDispatcher ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel(ioDispatcher) {

    private val _successProductImageState = MutableStateFlow<List<ImageMoreModel>>(emptyList())
    val successProductImageState: StateFlow<List<ImageMoreModel>> = _successProductImageState.asStateFlow()

    fun loadMoreImageProduct(id: String) {
        execute(loading = false, globalUiStateManager = globalUiStateManager) {
            val response = io {
                dataUseCase.loadProductImage(id)
            }
            _successProductImageState.value = response
        }
    }

}
