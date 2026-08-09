package com.tayler.repository.network.api

import com.tayler.repository.network.base.BaseNetwork
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Pruebas de integracion para UserNetwork.
 * Verifica que el mapeo de JSON a modelos de Kotlin funcione correctamente con Retrofit.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class UserNetworkIntegrationTest : BaseIntegrationTest() {

    private lateinit var userNetwork: UserNetwork

    @Before
    override fun setUp() {
        super.setUp()
        // Inicializamos UserNetwork con el serviceApi que apunta al MockWebServer
        userNetwork = UserNetwork(serviceApi, BaseNetwork())
    }

    @Test
    fun loadParam_returnsValidModelOnSuccess() = runTest {
        // Preparación: Definimos el JSON que el servidor simulado devolverá
        val jsonResponse = """
            {
                "uid": "test-id",
                "title": "Valu Shopping",
                "bgService": true,
                "textWelcome": "Bienvenido a Valu"
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        // Acción: Llamamos al método que queremos probar
        val result = userNetwork.loadParam("any-id")

        // Verificación: Comprobamos que Retrofit haya parseado bien los campos
        assertEquals("test-id", result.uid)
        assertEquals("Valu Shopping", result.title)
        assertEquals(true, result.bgService)
        assertEquals("Bienvenido a Valu", result.textWelcome)
    }

    @Test
    fun loadParam_handlesServerError404() = runTest {
        // Preparación: El servidor responde con un error 404
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        try {
            // Acción: Intentamos cargar los parámetros
            userNetwork.loadParam("missing-id")
        } catch (e: Exception) {
            // Verificación: Debería lanzar una excepción controlada (según BaseNetwork)
            // Aquí podrías verificar el tipo de excepción si lo tienes definido
        }
    }
}
