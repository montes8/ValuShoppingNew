package com.tayler.detail

import com.tayler.core.model.ImageMoreModel
import com.tayler.detail.domain.usecase.GetProductDetailUseCase
import com.tayler.ui.di.IoDispatcher
import com.tayler.ui.ui.base.BaseViewModel
import com.tayler.ui.ui.base.GlobalUiStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val globalUiStateManager: GlobalUiStateManager,
    @IoDispatcher ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel(ioDispatcher) {

    private val _successProductImageState = MutableStateFlow<List<ImageMoreModel>>(emptyList())
    val successProductImageState: StateFlow<List<ImageMoreModel>> = _successProductImageState.asStateFlow()

    fun loadMoreImageProduct(id: String) {
        execute(loading = false, globalUiStateManager = globalUiStateManager) {
            val response = io {
                getProductDetailUseCase(id)
            }
            _successProductImageState.value = response
        }
    }

}
