package com.tayler.repository.network.api

import com.tayler.entity.QuantumPublicKeyResponse
import com.tayler.repository.network.ServiceApi
import com.tayler.repository.network.base.BaseNetwork
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class QuantumNetworkTest {

    private lateinit var quantumNetwork: QuantumNetwork
    private val serviceApi: ServiceApi = mockk()
    private val baseNetwork = BaseNetwork()

    @Before
    fun setUp() {
        quantumNetwork = QuantumNetwork(serviceApi, baseNetwork)
    }

    @Test
    fun `getQuantumPublicKey returns data from api`() = runTest {
        val mockResponse = QuantumPublicKeyResponse(pubKeyBase64 = "key")
        coEvery { serviceApi.getQuantumPublicKey() } returns Response.success(mockResponse)

        val result = quantumNetwork.getQuantumPublicKey()

        assertEquals("key", result.pubKeyBase64)
    }
}
