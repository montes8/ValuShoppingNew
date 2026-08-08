package com.tayler.valushopping.ui.home.product

import com.tayler.entity.ProductModel
import com.tayler.usecases.DataUseCase
import com.tayler.valushopping.entity.AppDataVale
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

@HiltViewModel
class DataViewModel @Inject constructor(
    private val dataUseCase: DataUseCase,
    private val appDataVale: AppDataVale,
    private val globalUiStateManager: GlobalUiStateManager
) : BaseViewModel() {

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
            val response = dataUseCase.loadProduct(all, admin, country)
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
