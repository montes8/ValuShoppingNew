package com.tayler.valushopping.integration

import app.cash.turbine.test
import com.tayler.repository.network.ServiceApi
import com.tayler.repository.network.api.ConfigNetwork
import com.tayler.repository.network.base.BaseNetwork
import com.tayler.repository.network.model.response.CategoryResponse
import com.tayler.usecases.ConfigUseCase
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.rule.MainDispatcherRule
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import com.tayler.valushopping.ui.home.category.CategoryViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

/**
 * Prueba de Integracion de Cadena para el flujo de Categorias.
 * Valida: CategoryViewModel -> ConfigUseCase -> ConfigNetwork -> (ServiceApi Mock).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CategoryIntegrationTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val serviceApi: ServiceApi = mockk()
    
    private lateinit var configNetwork: ConfigNetwork
    private lateinit var configUseCase: ConfigUseCase
    private lateinit var appDataVale: AppDataVale
    private lateinit var globalUiStateManager: GlobalUiStateManager
    private lateinit var viewModel: CategoryViewModel

    @Before
    fun setUp() {
        // Unimos las piezas reales de la arquitectura
        configNetwork = ConfigNetwork(serviceApi, BaseNetwork())
        configUseCase = ConfigUseCase(configNetwork)
        appDataVale = AppDataVale()
        globalUiStateManager = GlobalUiStateManager()
        
        viewModel = CategoryViewModel(
            configUseCase,
            appDataVale,
            globalUiStateManager,
            testDispatcher
        )
    }

    @Test
    fun loadCategories_completesFullChainAndUpdatesState() = runTest(testDispatcher) {
        // 1. Preparacion: Simulamos datos de categorias desde la API
        val mockApiResponse = listOf(
            CategoryResponse(uid = "c1", name = "Smartphones")
        )
        coEvery { serviceApi.loadCategories() } returns Response.success(mockApiResponse)
        coEvery { serviceApi.loadCategoriesAll() } returns Response.success(mockApiResponse)

        // 2. Accion: Solicitamos carga desde el ViewModel
        viewModel.loadCategories()
        runCurrent()

        // 3. Verificacion: El dato debe fluir hasta el StateFlow
        viewModel.successCategoriesState.test {
            val categories = awaitItem()
            assertEquals("La cadena deberia haber entregado 1 categoria", 1, categories.size)
            assertEquals("Smartphones", categories[0].name)
        }
        
        // Tambien verificamos que se guardo en el objeto de datos globales
        assertEquals(1, appDataVale.categories.size)
    }
}
