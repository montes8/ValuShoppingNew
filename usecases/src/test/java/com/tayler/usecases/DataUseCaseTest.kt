package com.tayler.usecases

import com.tayler.entity.ImageMoreModel
import com.tayler.entity.ProductModel
import com.tayler.repository.network.protocol.IDataNetwork
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DataUseCaseTest {

    private lateinit var dataUseCase: DataUseCase
    private val dataNetwork: IDataNetwork = mockk()

    @Before
    fun setUp() {
        dataUseCase = DataUseCase(dataNetwork)
    }

    @Test
    fun loadProduct_returnsProductsFromRepository() = runTest {
        // Preparación: Definimos productos de prueba
        val mockProducts = listOf(ProductModel(uid = "1", name = "Prod"))
        coEvery { dataNetwork.loadProduct(any(), any(), any()) } returns mockProducts

        // Acción: Llamamos al caso de uso
        val result = dataUseCase.loadProduct(true, "admin", "PE")

        // Verificación: Comprobamos que los productos coincidan
        assertEquals(mockProducts, result)
    }

    @Test
    fun loadProductImage_returnsImagesFromRepository() = runTest {
        // Preparación: Definimos imágenes de prueba con todos sus campos requeridos
        val mockImages = listOf(
            ImageMoreModel(
                uid = "1", 
                name = "img", 
                idProduct = "p1", 
                idUser = "u1", 
                url = "url", 
                nameFile = "file"
            )
        )
        coEvery { dataNetwork.loadProductImage("p1") } returns mockImages

        // Acción: Obtenemos las imágenes
        val result = dataUseCase.loadProductImage("p1")

        // Verificación: Comprobamos la igualdad de la lista
        assertEquals(mockImages, result)
    }
}
