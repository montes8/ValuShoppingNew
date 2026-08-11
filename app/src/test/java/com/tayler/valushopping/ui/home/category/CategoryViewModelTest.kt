package com.tayler.valushopping.ui.home.category

import com.tayler.entity.CategoryModel
import com.tayler.usecases.ConfigUseCase
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.rule.MainDispatcherRule
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val configUseCase: ConfigUseCase = mockk()
    private val appDataVale = AppDataVale()
    private val globalUiStateManager = GlobalUiStateManager()
    private lateinit var viewModel: CategoryViewModel

    @Before
    fun setUp() {
        viewModel = CategoryViewModel(configUseCase, appDataVale, globalUiStateManager, testDispatcher)
    }

    @Test
    fun `loadCategories loads categories and updates appDataVale`() = runTest(testDispatcher) {
        val mockList = listOf(CategoryModel(uid = "c1", name = "Cat 1"))
        val mockAllList = listOf(CategoryModel(uid = "ca1", name = "All Cat 1"))
        
        coEvery { configUseCase.listCategories() } returns mockList
        coEvery { configUseCase.listCategoriesAll() } returns mockAllList

        viewModel.loadCategories()
        runCurrent()

        assertEquals(mockList.size, viewModel.successCategoriesState.value.size)
        assertEquals(mockList, appDataVale.categories)
        assertEquals(mockAllList, appDataVale.categoriesAll)
    }

    @Test
    fun `loadCategories does not reload if already loaded`() = runTest(testDispatcher) {
        // First load
        coEvery { configUseCase.listCategories() } returns listOf(CategoryModel(uid = "c1"))
        coEvery { configUseCase.listCategoriesAll() } returns emptyList()
        
        viewModel.loadCategories()
        runCurrent()
        
        // Second load call should not trigger usecase
        viewModel.loadCategories()
        runCurrent()

        io.mockk.coVerify(exactly = 1) { configUseCase.listCategories() }
    }
}
