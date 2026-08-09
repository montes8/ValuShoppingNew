package com.tayler.valushopping.integration

import app.cash.turbine.test
import com.tayler.entity.ProductModel
import com.tayler.repository.network.ServiceApi
import com.tayler.repository.network.api.DataNetwork
import com.tayler.repository.network.base.BaseNetwork
import com.tayler.repository.network.model.response.ProductResponse
import com.tayler.usecases.DataUseCase
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.rule.MainDispatcherRule
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import com.tayler.valushopping.ui.home.product.DataViewModel
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
import retrofit2.Response

/**
 * Prueba de Integracion de Cadena para el flujo de Productos.
 * Valida la union: DataViewModel -> DataUseCase -> DataNetwork -> (ServiceApi Mock).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ProductIntegrationTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    // Mockeamos solo la interfaz de salida (API)
    private val serviceApi: ServiceApi = mockk()

    // Instancias reales para probar la integracion de la cadena
    private lateinit var dataNetwork: DataNetwork
    private lateinit var dataUseCase: DataUseCase
    private lateinit var appDataVale: AppDataVale
    private lateinit var globalUiStateManager: GlobalUiStateManager
    private lateinit var viewModel: DataViewModel

    @Before
    fun setUp() {
        // Configuramos la cadena con objetos reales
        dataNetwork = DataNetwork(serviceApi, BaseNetwork())
        dataUseCase = DataUseCase(dataNetwork)
        appDataVale = AppDataVale()
        globalUiStateManager = GlobalUiStateManager()
        
        viewModel = DataViewModel(
            dataUseCase,
            appDataVale,
            globalUiStateManager,
            testDispatcher
        )
    }

    @Test
    fun loadProductClient_completesFullChainFromApiToViewModel() = runTest(testDispatcher) {
        // 1. Preparacion: Simulamos una respuesta real de la API (JSON mapeado a ProductResponse)
        val mockApiResponse = listOf(
            ProductResponse(uid = "p1", name = "Chain Product", price = "99.9")
        )
        coEvery { serviceApi.loadProduct("PE") } returns Response.success(mockApiResponse)

        // 2. Accion: El ViewModel dispara la peticion que viaja por toda la cadena
        viewModel.loadProductClient(country = "PE")
        runCurrent()

        // 3. Verificacion: Validamos que el dato llego al StateFlow del ViewModel tras pasar por Network y UseCase
        viewModel.successLoadProductClientState.test {
            val state = awaitItem() // Estado inicial o de carga
            val products = state.first
            
            assertTrue("La lista de productos no deberia estar vacia", products.isNotEmpty())
            assertEquals("Chain Product", products[0].name)
        }
    }
}
