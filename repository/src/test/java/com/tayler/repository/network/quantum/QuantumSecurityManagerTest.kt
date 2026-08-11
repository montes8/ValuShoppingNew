package com.tayler.repository.network.quantum

import android.util.Base64
import com.tayler.entity.QuantumPublicKeyResponse
import com.tayler.repository.network.protocol.IQuantumNetwork
import com.tayler.repository.preferences.manager.PreferencesManager
import com.tayler.repository.utils.PREFERENCE_QUANTUM_PACKAGE
import com.tayler.repository.utils.PREFERENCE_QUANTUM_SECRET
import com.valu.uitaycompose.security.encryption.quantum.UiTayQuantumEngine
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.mockkObject
import io.mockk.unmockkStatic
import io.mockk.unmockkObject
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuantumSecurityManagerTest {

    private lateinit var qsm: QuantumSecurityManager
    private val quantumNetwork: IQuantumNetwork = mockk()
    private val preferencesManager: PreferencesManager = mockk(relaxed = true)

    @Before
    fun setUp() {
        mockkStatic(Base64::class)
        mockkObject(UiTayQuantumEngine)
        qsm = QuantumSecurityManager(quantumNetwork, preferencesManager)
    }

    @After
    fun tearDown() {
        unmockkStatic(Base64::class)
        unmockkObject(UiTayQuantumEngine)
    }

    @Test
    fun `getHandshake returns saved data when available`() = runTest {
        val mockSecret = "secret-bytes".toByteArray()
        every { preferencesManager.getString(PREFERENCE_QUANTUM_PACKAGE) } returns "saved-pkg"
        every { preferencesManager.getString(PREFERENCE_QUANTUM_SECRET) } returns "saved-secret-b64"
        every { Base64.decode("saved-secret-b64", Base64.NO_WRAP) } returns mockSecret

        val result = qsm.getHandshake()

        assertEquals("saved-pkg", result.first)
        assertArrayEquals(mockSecret, result.second)
    }

    @Test
    fun `getHandshake performs handshake when no saved data`() = runTest {
        val mockSecret = "new-secret".toByteArray()
        every { preferencesManager.getString(any()) } returns ""
        coEvery { quantumNetwork.getQuantumPublicKey() } returns QuantumPublicKeyResponse("pub-key-b64")
        
        every { UiTayQuantumEngine.uiTaEncapsulate("pub-key-b64") } returns Pair("new-pkg", mockSecret)
        every { Base64.encodeToString(mockSecret, Base64.NO_WRAP) } returns "new-secret-b64"

        val result = qsm.getHandshake()

        assertEquals("new-pkg", result.first)
        assertArrayEquals(mockSecret, result.second)
        verify { preferencesManager.setValue(PREFERENCE_QUANTUM_PACKAGE, "new-pkg") }
        verify { preferencesManager.setValue(PREFERENCE_QUANTUM_SECRET, "new-secret-b64") }
    }

    @Test(expected = Exception::class)
    fun `getHandshake throws exception on network failure`() = runTest {
        every { preferencesManager.getString(any()) } returns ""
        coEvery { quantumNetwork.getQuantumPublicKey() } throws RuntimeException("Network error")

        qsm.getHandshake()
    }

    @Test
    fun `getSecretSync returns secret when available`() {
        val mockSecret = "secret".toByteArray()
        every { preferencesManager.getString(PREFERENCE_QUANTUM_SECRET) } returns "b64"
        every { Base64.decode("b64", Base64.NO_WRAP) } returns mockSecret

        val result = qsm.getSecretSync()
        assertArrayEquals(mockSecret, result)
    }

    @Test
    fun `getSecretSync returns null when not available`() {
        every { preferencesManager.getString(PREFERENCE_QUANTUM_SECRET) } returns ""
        assertNull(qsm.getSecretSync())
    }

    @Test
    fun `clearSession clears preferences`() {
        qsm.clearSession()
        verify { preferencesManager.setValue(PREFERENCE_QUANTUM_PACKAGE, any<String>()) }
        verify { preferencesManager.setValue(PREFERENCE_QUANTUM_SECRET, any<String>()) }
    }
}
