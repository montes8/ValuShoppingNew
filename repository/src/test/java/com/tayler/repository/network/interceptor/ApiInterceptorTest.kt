package com.tayler.repository.network.interceptor

import com.tayler.repository.network.quantum.QuantumSecurityManager
import com.tayler.repository.preferences.manager.PreferencesManager
import com.tayler.repository.utils.*
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.coVerify
import okhttp3.*
import org.junit.Before
import org.junit.Test
import javax.inject.Provider

class ApiInterceptorTest {

    private lateinit var interceptor: ApiInterceptor
    private val preferencesManager: PreferencesManager = mockk()
    private val qsm: QuantumSecurityManager = mockk()
    private val qsmProvider: Provider<QuantumSecurityManager> = Provider { qsm }
    private val chain: Interceptor.Chain = mockk()

    @Before
    fun setUp() {
        interceptor = ApiInterceptor(preferencesManager, qsmProvider)
        every { preferencesManager.getString(PREFERENCE_TOKEN) } returns ""
        every { chain.request() } returns Request.Builder().url("http://localhost/api").build()
    }

    @Test
    fun `intercept adds standard headers`() {
        val request = Request.Builder().url("http://localhost/api").build()
        every { chain.request() } returns request
        every { chain.proceed(any()) } returns mockk()

        interceptor.intercept(chain)

        verify {
            chain.proceed(match {
                it.header("Content-Type") == MY_CONTENT_TYPE &&
                it.header("x-os") == PLATFORM
            })
        }
    }

    @Test
    fun `intercept adds Authorization header when token exists`() {
        every { preferencesManager.getString(PREFERENCE_TOKEN) } returns "my-token"
        every { chain.proceed(any()) } returns mockk()

        interceptor.intercept(chain)

        verify {
            chain.proceed(match { it.header(AUTHORIZATION) == "my-token" })
        }
    }

    @Test
    fun `intercept performing quantum handshake for GET requests`() {
        val request = Request.Builder().url("http://localhost/api/data").get().build()
        every { chain.request() } returns request
        coEvery { qsm.getHandshake() } returns Pair("quantum-pkg", "secret".toByteArray())
        every { chain.proceed(any()) } returns mockk()

        interceptor.intercept(chain)

        coVerify {
            chain.proceed(match { it.header("x-quantum-package") == "quantum-pkg" })
        }
    }

    @Test
    fun `intercept skips quantum for public-key url`() {
        val request = Request.Builder().url("http://localhost/config/public-key").get().build()
        every { chain.request() } returns request
        every { chain.proceed(any()) } returns mockk()

        interceptor.intercept(chain)

        coVerify(exactly = 0) { qsm.getHandshake() }
    }
}
