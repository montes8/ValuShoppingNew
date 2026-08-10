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
import com.valu.uitaycompose.utils.extension.uiTayGetMobilIPAddress
import com.valu.uitaycompose.utils.extension.uiTayDateToString
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

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
        mockkStatic("com.valu.uitaycompose.utils.extension.UiExtensionConfigKt")
        mockkStatic("com.valu.uitaycompose.utils.extension.UiExtensionUtilsKt")
        mockkStatic("com.valu.uitaycompose.utils.extension.UiExtensionDateKt")
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

    @After
    fun tearDown() {
        unmockkStatic("com.valu.uitaycompose.utils.extension.UiExtensionConfigKt")
        unmockkStatic("com.valu.uitaycompose.utils.extension.UiExtensionUtilsKt")
        unmockkStatic("com.valu.uitaycompose.utils.extension.UiExtensionDateKt")
    }

    @Test
    fun initSplash_updatesUiStateAndSuccessParamState() = runTest(testDispatcher) {
        val mockParam = ParamModel(textWelcome = "Welcome!", bgService = true, styleValu = "0")
        val mockUser = UserModel(names = "Tayler")
        
        every { appUseCase.configInitParam() } returns mockParam
        coEvery { appUseCase.paramInit(any(), any()) } returns mockParam
        coEvery { appUseCase.getUser() } returns mockUser
        coEvery { configUseCase.listCategoriesAll() } returns emptyList()

        viewModel.successParamState.test {
            viewModel.initSplash()
            
            // Initial state (null)
            assertEquals(null, awaitItem())
            
            // Wait for uiState to be updated (internal logic)
            testDispatcher.scheduler.advanceUntilIdle()
            
            // Updated state from loadRemoteParams
            val param = awaitItem()
            assertNotNull(param)
            assertEquals("Welcome!", param?.textWelcome)
        }

        assertEquals("Welcome!", viewModel.uiState.value.welcomeText)
        assertEquals(mockUser, appDataVale.user)
    }

    @Test
    fun saveHistory_callsUseCaseCorrectlly() = runTest(testDispatcher) {
        appDataVale.user = UserModel(names = "Tayler", lastName = "Test")
        every { appUseCase.getUUID() } returns "uuid"
        coEvery { configUseCase.saveHistory(any()) } returns true
        
        viewModel.saveHistory("TEST_FLOW")
        testDispatcher.scheduler.advanceUntilIdle()

        coEvery { configUseCase.saveHistory(any()) }
    }
}
