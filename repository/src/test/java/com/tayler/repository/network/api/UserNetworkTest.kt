package com.tayler.repository.network.api

import com.tayler.repository.network.ServiceApi
import com.tayler.repository.network.base.BaseNetwork
import com.tayler.repository.network.model.response.ParamResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class UserNetworkTest {

    private lateinit var userNetwork: UserNetwork
    private val serviceApi: ServiceApi = mockk()
    private val baseNetwork = BaseNetwork()

    @Before
    fun setUp() {
        userNetwork = UserNetwork(serviceApi, baseNetwork)
    }

    @Test
    fun loadParam_getsParamsAndMapsToDomainModel() = runTest {
        // Preparación: Mockeamos la respuesta de la API para parámetros
        val mockResponse = ParamResponse(uid = "123", title = "Valu")
        coEvery { serviceApi.loadParam("123") } returns Response.success(mockResponse)

        // Acción: Solicitamos los parámetros
        val result = userNetwork.loadParam("123")

        // Verificación: Comprobamos que el mapeo sea correcto
        assertEquals("123", result.uid)
        assertEquals("Valu", result.title)
    }
}
