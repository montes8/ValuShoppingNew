package com.tayler.repository.network.quantum

import android.util.Base64
import com.tayler.entity.QuantumCipherData
import com.tayler.repository.network.protocol.IQuantumNetwork
import com.tayler.repository.preferences.manager.PreferencesManager
import com.tayler.repository.utils.PREFERENCE_QUANTUM_PACKAGE
import com.tayler.repository.utils.PREFERENCE_QUANTUM_SECRET
import com.valu.uitaycompose.security.encryption.quantum.UiTayQuantumEngine
import com.valu.uitaycompose.utils.UI_EMPTY
import kotlinx.serialization.json.Json
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuantumSecurityManager @Inject constructor(
    private val quantumNetwork: IQuantumNetwork,
    private val preferencesManager: PreferencesManager,
) {
    val json = Json { ignoreUnknownKeys = true }
    private val handshakeMutex = Mutex()

    suspend fun getHandshake(): Pair<String, ByteArray> = handshakeMutex.withLock {
        val savedPkg = preferencesManager.getString(PREFERENCE_QUANTUM_PACKAGE)
        val savedSecretB64 = preferencesManager.getString(PREFERENCE_QUANTUM_SECRET)

        if (savedPkg.isNotEmpty() && savedSecretB64.isNotEmpty()) {
            return@withLock Pair(savedPkg, Base64.decode(savedSecretB64, Base64.NO_WRAP))
        }
        try {
            val pubKeyResponse = quantumNetwork.getQuantumPublicKey()
            val (pkg, secret) = UiTayQuantumEngine.uiTaEncapsulate(pubKeyResponse.pubKeyBase64)
            preferencesManager.setValue(PREFERENCE_QUANTUM_PACKAGE, pkg)
            preferencesManager.setValue(PREFERENCE_QUANTUM_SECRET, Base64.encodeToString(secret, Base64.NO_WRAP))
            Pair(pkg, secret)
        } catch (e: Exception) {
            throw Exception("Fallo en el Handshake Poscuántico: ${e.message}")
        }
    }

    fun decrypt(data: QuantumCipherData, secret: ByteArray): String {
        return UiTayQuantumEngine.uiTayDecryptAesGcm(
            ciphertextB64 = data.ciphertext,
            ivB64 = data.iv,
            authTagB64 = data.authTag,
            aesKeyBytes = secret
        )
    }

    /**
     * Obtiene el secreto de forma síncrona para el ConverterFactory.
     */
    fun getSecretSync(): ByteArray? {
        val savedSecretB64 = preferencesManager.getString(PREFERENCE_QUANTUM_SECRET)
        return if (savedSecretB64.isNotEmpty()) {
            Base64.decode(savedSecretB64, Base64.NO_WRAP)
        } else null
    }

    fun clearSession() {
        preferencesManager.setValue(PREFERENCE_QUANTUM_PACKAGE, UI_EMPTY)
        preferencesManager.setValue(PREFERENCE_QUANTUM_SECRET, UI_EMPTY)
    }
}
