package com.tayler.valushopping.ui.home.category

import com.tayler.entity.CategoryModel
import com.tayler.usecases.ConfigUseCase
import com.tayler.valushopping.di.IoDispatcher
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.ui.base.BaseViewModel
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import com.valu.uitaycompose.utils.UI_EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val configUseCase: ConfigUseCase,
    private val appDataVale: AppDataVale,
    private val globalUiStateManager: GlobalUiStateManager,
    @IoDispatcher ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel(ioDispatcher) {

    private val _successCategoriesState = MutableStateFlow(
        List(4) { CategoryModel(uid = UI_EMPTY, name = UI_EMPTY, url = UI_EMPTY) }
    )
    val successCategoriesState: StateFlow<List<CategoryModel>> = _successCategoriesState.asStateFlow()

    fun loadCategories() {
        val currentList = _successCategoriesState.value
        val isAlreadyLoaded = currentList.firstOrNull()?.uid?.isNotEmpty() == true
        if (isAlreadyLoaded) return

        execute(loading = false, globalUiStateManager = globalUiStateManager) {
            val response = configUseCase.listCategories()
            val responseAll = configUseCase.listCategoriesAll()
            appDataVale.categories = response
            appDataVale.categoriesAll = responseAll
            _successCategoriesState.value = response.shuffled()
        }
    }
}