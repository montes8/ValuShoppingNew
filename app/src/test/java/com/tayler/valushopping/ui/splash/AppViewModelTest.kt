package com.tayler.valushopping.ui.splash

import android.app.Application
import app.cash.turbine.test
import com.tayler.entity.ParamModel
import com.tayler.entity.UserModel
import com.tayler.usecases.AppUseCase
import com.tayler.usecases.ConfigUseCase
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.rule.MainDispatcherRule
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import com.valu.uitaycompose.utils.extension.uiTayCountryNetwork
import com.valu.uitaycompose.utils.extension.uiTayGetAndroidId
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppViewModelTest {

    private val testDispatcher = kotlinx.coroutines.test.StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val configUseCase: ConfigUseCase = mockk()
    private val appUseCase: AppUseCase = mockk()
    private val application: Application = mockk()
    private val appDataVale = AppDataVale()
    private val globalUiStateManager = GlobalUiStateManager()

    private lateinit var viewModel: AppViewModel

    @Before
    fun setUp() {
        mockkStatic(Application::uiTayGetAndroidId)
        mockkStatic(Application::uiTayCountryNetwork)
        mockkStatic(android.util.Log::class)
        every { android.util.Log.d(any(), any()) } returns 0
        every { application.uiTayGetAndroidId() } returns "test-android-id"
        every { application.uiTayCountryNetwork() } returns "PE"

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
    fun initSplash_updatesUiStateAndSuccessParamState() = runTest(testDispatcher) {
        val mockParam = ParamModel(textWelcome = "Welcome!", bgService = true, styleValu = "0")
        val mockUser = UserModel(names = "Tayler")
        
        every { appUseCase.configInitParam() } returns mockParam
        coEvery { appUseCase.paramInit(any(), any()) } returns mockParam
        coEvery { appUseCase.getUser() } returns mockUser
        coEvery { configUseCase.listCategoriesAll() } returns emptyList()

        viewModel.uiState.test {
            viewModel.initSplash()
            
            // El primer item es el estado inicial (vacio)
            assertEquals("", awaitItem().welcomeText)
            
            // El segundo item debe ser el estado actualizado
            val state = awaitItem() 
            assertEquals("Welcome!", state.welcomeText)
            assertEquals(true, state.showLogo)
        }

        viewModel.successParamState.test {
            // El primer item es el estado inicial (null)
            assertEquals(null, awaitItem())

            // El segundo item debe ser el objeto ParamModel cargado
            val param = awaitItem()
            assertNotNull(param)
            assertEquals("Welcome!", param?.textWelcome)
        }
        
        assertEquals(mockUser, appDataVale.user)
    }
}
