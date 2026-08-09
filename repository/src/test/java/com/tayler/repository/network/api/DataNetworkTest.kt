package com.tayler.repository.network.api

import com.tayler.repository.network.ServiceApi
import com.tayler.repository.network.base.BaseNetwork
import com.tayler.repository.network.model.response.ProductResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class DataNetworkTest {

    private lateinit var dataNetwork: DataNetwork
    private val serviceApi: ServiceApi = mockk()
    private val baseNetwork = BaseNetwork()

    @Before
    fun setUp() {
        dataNetwork = DataNetwork(serviceApi, baseNetwork)
    }

    @Test
    fun loadProduct_getsProductsFilteredByCountry() = runTest {
        // Preparación: Mockeamos una lista de productos
        val mockList = listOf(ProductResponse(uid = "p1", name = "Laptop"))
        coEvery { serviceApi.loadProduct("PE") } returns Response.success(mockList)

        // Acción: Cargamos productos para un país específico
        val result = dataNetwork.loadProduct(all = false, isUser = "any", country = "PE")

        // Verificación: Comprobamos el tamaño y contenido de la lista mapeada
        assertEquals(1, result.size)
        assertEquals("Laptop", result[0].name)
    }
}
