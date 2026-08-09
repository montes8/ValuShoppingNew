package com.tayler.valushopping.ui.home.category

import app.cash.turbine.test
import com.tayler.entity.CategoryModel
import com.tayler.usecases.ConfigUseCase
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.rule.MainDispatcherRule
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import io.mockk.coEvery
import io.mockk.coVerify
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
    fun loadCategories_loadsCategoriesAndUpdateAppDataVale() = runTest(testDispatcher) {
        // Preparación: Definimos categorías de prueba
        val mockCategories = listOf(CategoryModel(uid = "1", name = "Cat 1", url = "url1"))
        coEvery { configUseCase.listCategories() } returns mockCategories
        coEvery { configUseCase.listCategoriesAll() } returns mockCategories

        // Acción: Iniciamos la carga de categorías
        viewModel.loadCategories()
        runCurrent()

        // Verificación: Comprobamos que el estado del ViewModel y AppDataVale se actualicen
        viewModel.successCategoriesState.test {
            val state = awaitItem()
            assertEquals(1, state.size)
            assertEquals("Cat 1", state[0].name)
        }
        assertEquals(mockCategories, appDataVale.categories)
        assertEquals(mockCategories, appDataVale.categoriesAll)
    }

    @Test
    fun loadCategories_doesNotReloadIfAlreadyLoaded() = runTest(testDispatcher) {
        // Preparación: Simulamos que ya hay categorías cargadas en el estado
        val mockCategories = listOf(CategoryModel(uid = "1", name = "Cat 1", url = "url1"))
        coEvery { configUseCase.listCategories() } returns mockCategories
        coEvery { configUseCase.listCategoriesAll() } returns mockCategories

        // Primera carga
        viewModel.loadCategories()
        runCurrent()

        // Intento de segunda carga
        viewModel.loadCategories()
        runCurrent()

        // Verificación: Se debe haber llamado al UseCase solo una vez
        coVerify(exactly = 1) { configUseCase.listCategories() }
    }
}
