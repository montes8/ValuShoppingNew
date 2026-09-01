package com.tayler.home.ui.product

import com.tayler.core.model.ProductModel
import com.tayler.home.domain.usecase.GetHomeProductsUseCase
import com.tayler.ui.di.IoDispatcher
import com.tayler.ui.AppDataVale
import com.tayler.ui.ui.base.BaseViewModel
import com.tayler.ui.ui.base.GlobalUiStateManager
import com.tayler.core.common.utils.TY_DEFAULT
import com.tayler.ui.distance
import com.valu.uitaycompose.utils.UI_EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getHomeProductsUseCase: GetHomeProductsUseCase,
    private val appDataVale: AppDataVale,
    private val globalUiStateManager: GlobalUiStateManager,
    @IoDispatcher ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel(ioDispatcher) {

    private val _successLoadProductClientState = MutableStateFlow(
        Triple(
            List(5) { ProductModel(name = UI_EMPTY, url = UI_EMPTY) },
            List(1) { ProductModel(name = UI_EMPTY, urlBanner = UI_EMPTY) }, false
        )
    )
    val successLoadProductClientState: StateFlow<Triple<List<ProductModel>, List<ProductModel>, Boolean>> =
        _successLoadProductClientState.asStateFlow()


    fun loadProductClient(
        all: Boolean = false,
        admin: String = UI_EMPTY,
        location: Boolean = false,
        country: String
    ) {
        val currentProducts = _successLoadProductClientState.value.first
        val isAlreadyLoaded = currentProducts.firstOrNull()?.uid?.isNotEmpty() == true
        if (isAlreadyLoaded) return

        execute(loading = false, globalUiStateManager = globalUiStateManager) {
            val listFilter: ArrayList<ProductModel> = ArrayList()
            val response = io { getHomeProductsUseCase(all, admin, country) }
            val listBanner = response.filter { it.banner }
            response.forEach {
                val distanceM = it.distance("K", appDataVale)
                if ((distanceM < getRangeFilterProduct(it)) || it.latitude == "0") {
                    listFilter.add(it)
                }
            }

            _successLoadProductClientState.value =
                Triple(if (location) listFilter.shuffled() else response.shuffled(), listBanner, true)
        }
    }

    private fun getRangeFilterProduct(it: ProductModel): Int {
        return if (it.limitDistance.isEmpty() || it.limitDistance == TY_DEFAULT)
            appDataVale.paramData.limitDistance?.toInt() ?: 5
        else it.limitDistance.toInt()
    }
}
