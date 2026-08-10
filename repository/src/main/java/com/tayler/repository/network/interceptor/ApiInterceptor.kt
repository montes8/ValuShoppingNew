package com.tayler.repository.network.interceptor

import com.tayler.repository.network.quantum.QuantumSecurityManager
import com.tayler.repository.preferences.manager.PreferencesManager
import com.tayler.repository.utils.AUTHORIZATION
import com.tayler.repository.utils.MY_CONTENT_TYPE
import com.tayler.repository.utils.PLATFORM
import com.tayler.repository.utils.PREFERENCE_TOKEN
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Provider

/**
 * Interceptor optimizado: Solo maneja Headers.
 * El descifrado se delega al QuantumConverterFactory para máxima limpieza.
 */
class ApiInterceptor @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val quantumSecurityManagerProvider: Provider<QuantumSecurityManager>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
            .addHeader("Content-Type", MY_CONTENT_TYPE)
            .header("x-os", PLATFORM)
        
        val token = preferencesManager.getString(PREFERENCE_TOKEN)
        if (token.isNotEmpty()) {
            builder.addHeader(AUTHORIZATION, token)
        }

        if (request.method == "GET" && !request.url.toString().contains("public-key")) {
            val qsm = quantumSecurityManagerProvider.get()
            
            val (pkg, _) = try {
                runBlocking { qsm.getHandshake() }
            } catch (e: Exception) {
                return chain.proceed(builder.build())
            }
            
            builder.header("x-quantum-package", pkg)
        }
        
        return chain.proceed(builder.build())
    }
}
