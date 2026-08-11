package com.tayler.repository.network.quantum

import com.tayler.entity.QuantumEncryptedResponse
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Provider

/**
 * Esta es la solución de "Clase Mundial" (Senior).
 * Se encarga de interceptar la respuesta de Retrofit ANTES de que llegue al Repositorio.
 * Abre el sobre 'QuantumEncryptedResponse', descifra el JSON y lo mapea al objeto final.
 */
class QuantumConverterFactory @Inject constructor(
    private val qsmProvider: Provider<QuantumSecurityManager>,
) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val delegate = retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        
        return Converter<ResponseBody, Any> { value ->
            val qsm = qsmProvider.get()
            val bodyString = value.string()
            
            try {
                val wrapper = qsm.json.decodeFromString<QuantumEncryptedResponse>(bodyString)
                val data = wrapper.encryptedResponse
                
                if (data != null) {
                    val secret = qsm.getSecretSync() 
                        ?: throw Exception("No hay sesión cuántica activa")
                    val decryptedJson = qsm.decrypt(data, secret)
                    val responseBody = decryptedJson.toResponseBody(value.contentType())
                    delegate.convert(responseBody)
                } else {
                    delegate.convert(bodyString.toResponseBody(value.contentType()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                delegate.convert(bodyString.toResponseBody(value.contentType()))
            }
        }
    }
}
