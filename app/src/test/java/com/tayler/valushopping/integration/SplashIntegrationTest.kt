package com.tayler.valushopping.integration

import android.app.Application
import app.cash.turbine.test
import com.tayler.repository.network.ServiceApi
import com.tayler.repository.network.api.ConfigNetwork
import com.tayler.repository.network.api.UserNetwork
import com.tayler.repository.network.base.BaseNetwork
import com.tayler.repository.network.model.response.ParamResponse
import com.tayler.usecases.AppUseCase
import com.tayler.usecases.ConfigUseCase
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.rule.MainDispatcherRule
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import com.tayler.valushopping.ui.splash.AppViewModel
import com.valu.uitaycompose.utils.extension.uiTayCountryNetwork
import com.valu.uitaycompose.utils.extension.uiTayGetAndroidId
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

/**
 * Prueba de Integracion de Cadena para el Splash.
 * Valida: AppViewModel -> AppUseCase/ConfigUseCase -> UserNetwork/ConfigNetwork -> ServiceApi.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SplashIntegrationTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val serviceApi: ServiceApi = mockk()
    private val application: Application = mockk()

    private lateinit var userNetwork: UserNetwork
    private lateinit var configNetwork: ConfigNetwork
    private lateinit var appUseCase: AppUseCase
    private lateinit var configUseCase: ConfigUseCase
    private lateinit var appDataVale: AppDataVale
    private lateinit var globalUiStateManager: GlobalUiStateManager
    private lateinit var viewModel: AppViewModel

    @Before
    fun setUp() {
        // Mocks de utilidades estaticas de Android
        mockkStatic(Application::uiTayGetAndroidId)
        mockkStatic(Application::uiTayCountryNetwork)
        mockkStatic(android.util.Log::class)
        every { android.util.Log.d(any(), any()) } returns 0
        every { application.uiTayGetAndroidId() } returns "android-id"
        every { application.uiTayCountryNetwork() } returns "PE"

        // Montamos la cadena real
        val baseNetwork = BaseNetwork()
        userNetwork = UserNetwork(serviceApi, baseNetwork)
        configNetwork = ConfigNetwork(serviceApi, baseNetwork)
        
        // Mockeamos AppPreferences ya que es la persistencia local, pero usamos UseCase real
        val appPreferences = mockk<com.tayler.repository.preferences.IAppPreferences>(relaxed = true)
        every { appPreferences.getUUID() } returns "uuid"
        
        appUseCase = AppUseCase(appPreferences, userNetwork, configNetwork)
        configUseCase = ConfigUseCase(configNetwork)
        
        appDataVale = AppDataVale()
        globalUiStateManager = GlobalUiStateManager()

        viewModel = AppViewModel(
            configUseCase,
            appUseCase,
            application,
            appDataVale,
            globalUiStateManager,
            testDispatcher
        )
    }

    @Test
    fun initSplash_completesFullChainWithNetworkData() = runTest(testDispatcher) {
        // 1. Preparacion: Respuesta de la API para parametros remotos
        val mockParamResponse = ParamResponse(uid = "p1", title = "Integration Valu")
        coEvery { serviceApi.loadParam(any()) } returns Response.success(mockParamResponse)
        coEvery { serviceApi.loadUserBlocking() } returns Response.success(emptyList())
        coEvery { serviceApi.loadCategoriesAll() } returns Response.success(emptyList())

        // 2. Accion y Verificacion: Los datos deben haber fluido hasta el estado del ViewModel
        viewModel.successParamState.test {
            viewModel.initSplash()
            runCurrent()
            
            assertEquals(null, awaitItem()) // Estado inicial
            val param = awaitItem()
            assertNotNull(param)
            assertEquals("Integration Valu", param?.title)
        }
    }
}
