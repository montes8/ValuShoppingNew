package com.tayler.repository.network.interceptor

import android.content.Context
import com.tayler.entity.exception.MyNetworkException
import com.tayler.repository.utils.isAirplaneModeActive
import com.tayler.repository.utils.isConnected
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import okhttp3.Interceptor
import org.junit.After
import org.junit.Before
import org.junit.Test

class ConnectivityInterceptorTest {

    private lateinit var interceptor: ConnectivityInterceptor
    private val context: Context = mockk()
    private val chain: Interceptor.Chain = mockk()

    @Before
    fun setUp() {
        interceptor = ConnectivityInterceptor(context)
        mockkStatic("com.tayler.repository.utils.ExtensionNetworkKt")
    }

    @After
    fun tearDown() {
        unmockkStatic("com.tayler.repository.utils.ExtensionNetworkKt")
    }

    @Test
    fun `intercept proceeds when connected and not in airplane mode`() {
        every { context.isConnected() } returns true
        every { context.isAirplaneModeActive() } returns false
        every { chain.request() } returns mockk()
        every { chain.proceed(any()) } returns mockk()

        interceptor.intercept(chain)
    }

    @Test(expected = MyNetworkException::class)
    fun `intercept throws MyNetworkException when not connected`() {
        every { context.isConnected() } returns false
        every { context.isAirplaneModeActive() } returns false

        interceptor.intercept(chain)
    }

    @Test(expected = MyNetworkException::class)
    fun `intercept throws MyNetworkException when in airplane mode`() {
        every { context.isConnected() } returns true
        every { context.isAirplaneModeActive() } returns true

        interceptor.intercept(chain)
    }
}
