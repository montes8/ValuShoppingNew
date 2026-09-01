package com.tayler.home.ui.category

import com.tayler.core.model.CategoryModel
import com.tayler.home.domain.usecase.GetCategoriesUseCase
import com.tayler.ui.di.IoDispatcher
import com.tayler.ui.AppDataVale
import com.tayler.ui.ui.base.BaseViewModel
import com.tayler.ui.ui.base.GlobalUiStateManager
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
    private val getCategoriesUseCase: GetCategoriesUseCase,
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
            val response = io { getCategoriesUseCase.getCategories() }
            val responseAll = io { getCategoriesUseCase.getCategoriesAll() }
            appDataVale.categories = response
            appDataVale.categoriesAll = responseAll
            _successCategoriesState.value = response.shuffled()
        }
    }
}