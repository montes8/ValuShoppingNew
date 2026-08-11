package com.tayler.repository.network.base

import com.tayler.entity.exception.UiTayApiException
import com.tayler.repository.utils.toAppException
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class BaseNetworkTest {

    private val baseNetwork = BaseNetwork()

    @Before
    fun setUp() {
        mockkStatic("com.tayler.repository.utils.ExtensionNetworkKt")
    }

    @After
    fun tearDown() {
        unmockkStatic("com.tayler.repository.utils.ExtensionNetworkKt")
    }

    @Test
    fun `safeApiCall returns data on success`() = runTest {
        val result = baseNetwork.safeApiCall { "success" }
        assertEquals("success", result)
    }

    @Test(expected = UiTayApiException::class)
    fun `safeApiCall rethrows as app exception on failure`() = runTest {
        val ioException = IOException("network error")
        every { ioException.toAppException() } returns UiTayApiException(0, "Error", "Mapped")
        
        baseNetwork.safeApiCall { throw ioException }
    }

    @Test(expected = CancellationException::class)
    fun `safeApiCall rethrows CancellationException directly`() = runTest {
        baseNetwork.safeApiCall { throw CancellationException() }
    }
}
