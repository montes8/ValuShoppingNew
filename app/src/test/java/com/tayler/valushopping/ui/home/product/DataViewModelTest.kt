package com.tayler.valushopping.ui.home.product

import app.cash.turbine.test
import com.tayler.entity.ProductModel
import com.tayler.usecases.DataUseCase
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.rule.MainDispatcherRule
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import com.tayler.valushopping.utils.distance
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
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
class DataViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val dataUseCase: DataUseCase = mockk()
    private val appDataVale = AppDataVale()
    private val globalUiStateManager = GlobalUiStateManager()
    private lateinit var viewModel: DataViewModel

    @Before
    fun setUp() {
        mockkStatic("com.tayler.valushopping.utils.ExtensionUtilsKt")
        viewModel = DataViewModel(dataUseCase, appDataVale, globalUiStateManager, testDispatcher)
    }

    @Test
    fun loadProductClient_filtersByDistanceAndUpdateState() = runTest(testDispatcher) {
        // Preparación: Definimos productos, uno dentro del rango y otro fuera
        val productInRange = ProductModel(uid = "1", name = "Prod 1", latitude = "10.0", longitude = "10.0")
        val productOut = ProductModel(uid = "2", name = "Prod 2", latitude = "50.0", longitude = "50.0")
        
        coEvery { dataUseCase.loadProduct(any(), any(), any()) } returns listOf(productInRange, productOut)
        
        // Mockeamos la extensión de distancia
        every { productInRange.distance(any(), any()) } returns 2.0
        every { productOut.distance(any(), any()) } returns 100.0
        
        appDataVale.paramData = appDataVale.paramData.copy(limitDistance = "10")

        // Acción: Cargamos productos con filtro de ubicación
        viewModel.loadProductClient(location = true, country = "PE")
        runCurrent()

        // Verificación: Solo el producto en rango debería estar en la lista filtrada
        viewModel.successLoadProductClientState.test {
            val state = awaitItem()
            val filteredList = state.first
            assertTrue(filteredList.any { it.uid == "1" })
            assertEquals(1, filteredList.size)
        }
    }
}
