package com.tayler.valushopping.integration

import app.cash.turbine.test
import com.tayler.entity.UserModel
import com.tayler.repository.network.protocol.IConfigNetwork
import com.tayler.repository.network.protocol.IUserNetwork
import com.tayler.repository.preferences.api.AppPreferences
import com.tayler.repository.preferences.manager.PreferencesManager
import com.tayler.repository.utils.toJson
import com.tayler.usecases.AppUseCase
import com.tayler.valushopping.rule.MainDispatcherRule
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import com.tayler.valushopping.ui.profile.UserViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Prueba de Integracion de Cadena para el flujo de Usuario y Preferencias.
 * Valida: UserViewModel -> AppUseCase -> AppPreferences -> (PreferencesManager Mock).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class UserIntegrationTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    // Mockeamos la capa de persistencia final
    private val preferencesManager: PreferencesManager = mockk(relaxed = true)

    private lateinit var appPreferences: AppPreferences
    private lateinit var appUseCase: AppUseCase
    private lateinit var globalUiStateManager: GlobalUiStateManager
    private lateinit var viewModel: UserViewModel

    @Before
    fun setUp() {
        // Objetos de red mockeados (no se usan en este test pero se inyectan)
        val userNetwork: IUserNetwork = mockk()
        val configNetwork: IConfigNetwork = mockk()

        // Cadena real
        appPreferences = AppPreferences(preferencesManager)
        appUseCase = AppUseCase(appPreferences, userNetwork, configNetwork)
        globalUiStateManager = GlobalUiStateManager()
        
        viewModel = UserViewModel(
            appUseCase,
            globalUiStateManager,
            testDispatcher
        )
    }

    @Test
    fun saveAndLoadUser_completesChainThroughPreferences() = runTest(testDispatcher) {
        // 1. Preparacion: Datos de usuario y simulacion de lectura de JSON
        val mockUser = UserModel(uid = "u1", names = "Integration User")
        every { preferencesManager.getString(any()) } returns mockUser.toJson()

        // 2. Accion: Cargamos el usuario a traves de la cadena real
        viewModel.loadUser()
        runCurrent()

        // 3. Verificacion: El ViewModel debe tener el dato leido desde las preferencias (mockeadas)
        viewModel.successUserState.test {
            val user = awaitItem()
            assertEquals("Integration User", user?.names)
        }
    }
}
