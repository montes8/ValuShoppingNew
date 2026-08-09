package com.tayler.repository.network.api

import com.tayler.repository.network.ServiceApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Clase base para pruebas de integracion que requieren simular un servidor real.
 * Se encarga de iniciar y detener el MockWebServer automaticamente.
 */
abstract class BaseIntegrationTest {

    protected lateinit var mockWebServer: MockWebServer
    protected lateinit var serviceApi: ServiceApi

    @Before
    open fun setUp() {
        // Iniciamos el servidor simulado
        mockWebServer = MockWebServer()
        mockWebServer.start()

        // Configuramos Retrofit para que apunte a la URL del servidor simulado
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        serviceApi = retrofit.create(ServiceApi::class.java)
    }

    @After
    open fun tearDown() {
        // Detenemos el servidor al finalizar cada prueba
        mockWebServer.shutdown()
    }
}
