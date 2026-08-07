package com.tayler.valushopping.ui.detail

import com.tayler.entity.ImageMoreModel
import com.tayler.entity.ProductImageModel
import com.tayler.entity.ProductModel
import com.tayler.usecases.DataUseCase
import com.tayler.valushopping.ui.base.BaseViewModel
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import com.tayler.valushopping.utils.TY_DEFAULT
import com.tayler.valushopping.utils.distance
import com.valu.uitaycompose.utils.UI_EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import java.io.File

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dataUseCase: DataUseCase,
    private val globalUiStateManager: GlobalUiStateManager
) : BaseViewModel() {

    private val _successProductImageState = MutableStateFlow<List<ImageMoreModel>>(emptyList())
    val successProductImageState: StateFlow<List<ImageMoreModel>> = _successProductImageState.asStateFlow()

    fun loadMoreImageProduct(id: String) {
        execute(loading = false, globalUiStateManager = globalUiStateManager) {
            val response = dataUseCase.loadProductImage(id)
            _successProductImageState.value = response
        }
    }

}