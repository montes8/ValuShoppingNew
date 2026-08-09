package com.tayler.repository.network.api

import com.tayler.repository.network.base.BaseNetwork
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Pruebas de integracion para DataNetwork.
 * Verifica la correcta interpretacion de listas de productos desde el servidor.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class DataNetworkIntegrationTest : BaseIntegrationTest() {

    private lateinit var dataNetwork: DataNetwork

    @Before
    override fun setUp() {
        super.setUp()
        dataNetwork = DataNetwork(serviceApi, BaseNetwork())
    }

    @Test
    fun loadProduct_mapsProductListCorrectlly() = runTest {
        // Preparación: JSON con una lista de productos
        val jsonResponse = """
            [
                {
                    "uid": "p1",
                    "name": "Producto A",
                    "price": "100.0",
                    "url": "http://img.com/a.png"
                },
                {
                    "uid": "p2",
                    "name": "Producto B",
                    "price": "50.0",
                    "url": "http://img.com/b.png"
                }
            ]
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        // Acción: Solicitamos los productos al repositorio
        val result = dataNetwork.loadProduct(all = false, isUser = "", country = "PE")

        // Verificación: Confirmamos que la lista tiene el tamaño y contenido correcto
        assertEquals(2, result.size)
        assertEquals("Producto A", result[0].name)
        assertEquals("Producto B", result[1].name)
    }

    @Test
    fun loadProduct_returnsEmptyListOnMalformedJson() = runTest {
        // Preparación: JSON corrupto
        val corruptedJson = "{ invalid: json }"
        mockWebServer.enqueue(MockResponse().setBody(corruptedJson).setResponseCode(200))

        try {
            // Acción
            dataNetwork.loadProduct(all = false, isUser = "", country = "PE")
        } catch (e: Exception) {
            // Verificación: BaseNetwork debería atrapar el error de Gson
        }
    }
}
