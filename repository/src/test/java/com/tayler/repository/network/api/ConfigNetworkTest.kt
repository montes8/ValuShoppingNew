package com.tayler.repository.network.api

import com.tayler.repository.network.ServiceApi
import com.tayler.repository.network.base.BaseNetwork
import com.tayler.repository.network.model.response.CategoryResponse
import com.tayler.repository.network.model.response.UserBlockingResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ConfigNetworkTest {

    private lateinit var configNetwork: ConfigNetwork
    private val serviceApi: ServiceApi = mockk()
    private val baseNetwork = BaseNetwork()

    @Before
    fun setUp() {
        configNetwork = ConfigNetwork(serviceApi, baseNetwork)
    }

    @Test
    fun `loadBlocking returns mapped list on success`() = runTest {
        val mockResponse = listOf(UserBlockingResponse(imei = "123"))
        coEvery { serviceApi.loadUserBlocking() } returns Response.success(mockResponse)

        val result = configNetwork.loadBlocking()

        assertEquals(1, result.size)
        assertEquals("123", result[0].imei)
    }

    @Test
    fun `loadBlocking returns empty list on error`() = runTest {
        coEvery { serviceApi.loadUserBlocking() } throws RuntimeException("Network error")

        val result = configNetwork.loadBlocking()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `listCategories returns mapped list on success`() = runTest {
        val mockResponse = listOf(CategoryResponse(uid = "cat1", name = "Electronics"))
        coEvery { serviceApi.loadCategories() } returns Response.success(mockResponse)

        val result = configNetwork.listCategories()

        assertEquals(1, result.size)
        assertEquals("Electronics", result[0].name)
    }
}
