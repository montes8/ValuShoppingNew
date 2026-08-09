package com.tayler.valushopping.ui.detail

import app.cash.turbine.test
import com.tayler.entity.ImageMoreModel
import com.tayler.usecases.DataUseCase
import com.tayler.valushopping.rule.MainDispatcherRule
import com.tayler.valushopping.ui.base.GlobalUiStateManager
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

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val dataUseCase: DataUseCase = mockk()
    private val globalUiStateManager = GlobalUiStateManager()
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        viewModel = DetailViewModel(dataUseCase, globalUiStateManager, testDispatcher)
    }

    @Test
    fun loadMoreImageProduct_updatesStateWithImages() = runTest(testDispatcher) {
        // Preparación: Definimos imágenes con todos los campos requeridos por el modelo
        val mockImages = listOf(
            ImageMoreModel(
                uid = "1", 
                name = "img1", 
                idProduct = "prod1", 
                idUser = "user1", 
                url = "http://image.com/1", 
                nameFile = "file1"
            )
        )
        coEvery { dataUseCase.loadProductImage("prod1") } returns mockImages

        // Acción: Cargamos más imágenes del producto
        viewModel.loadMoreImageProduct("prod1")
        runCurrent()

        // Verificación: Comprobamos que el StateFlow refleje las imágenes cargadas
        viewModel.successProductImageState.test {
            assertEquals(mockImages, awaitItem())
        }
    }
}
