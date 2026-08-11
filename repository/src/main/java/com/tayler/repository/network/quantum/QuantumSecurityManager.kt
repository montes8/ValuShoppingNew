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
    private val preferencesManager: PreferencesManager
) {
    val json = Json { ignoreUnknownKeys = true }
    private val handshakeMutex = Mutex()
    private var isSessionFresh = false // Bandera en memoria para la sesión actual

    /**
     * Obtiene o genera el handshake cuántico. 
     * Garantiza una llave nueva por cada inicio de la aplicación.
     */
    suspend fun getHandshake(): Pair<String, ByteArray> = handshakeMutex.withLock {
        val savedPkg = preferencesManager.getString(PREFERENCE_QUANTUM_PACKAGE)
        val savedSecretB64 = preferencesManager.getString(PREFERENCE_QUANTUM_SECRET)

        // Si ya hicimos el handshake en esta sesión de la app, reutilizamos
        if (isSessionFresh && savedPkg.isNotEmpty() && savedSecretB64.isNotEmpty()) {
            return@withLock Pair(savedPkg, Base64.decode(savedSecretB64, Base64.NO_WRAP))
        }

        // Si es la primera vez que se abre la app (o reinició el servidor), forzamos handshake
        try {
            val pubKeyResponse = quantumNetwork.getQuantumPublicKey()
            val (pkg, secret) = UiTayQuantumEngine.uiTaEncapsulate(pubKeyResponse.pubKeyBase64)
            
            preferencesManager.setValue(PREFERENCE_QUANTUM_PACKAGE, pkg)
            preferencesManager.setValue(PREFERENCE_QUANTUM_SECRET, Base64.encodeToString(secret, Base64.NO_WRAP))
            
            isSessionFresh = true // Marcamos que la sesión ya es válida para esta ejecución
            Pair(pkg, secret)
        } catch (e: Exception) {
            isSessionFresh = false
            throw Exception("Fallo en el Handshake Poscuántico: ${e.message}")
        }
    }

    fun decrypt(data: QuantumCipherData, secret: ByteArray): String {
        return try {
            UiTayQuantumEngine.uiTayDecryptAesGcm(
                ciphertextB64 = data.ciphertext,
                ivB64 = data.iv,
                authTagB64 = data.authTag,
                aesKeyBytes = secret,
            )
        } catch (e: Exception) {
            // Si falla el descifrado, es muy probable que la sesión ya no sea válida
            clearSession()
            throw Exception("Error de descifrado (Sesión inválida): ${e.message}")
        }
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
