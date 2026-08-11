package com.tayler.repository.network.quantum

import com.tayler.entity.QuantumEncryptedResponse
import com.tayler.repository.utils.uiTayLog
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Provider

/**
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
            
            uiTayLog(bodyString, "QUANTUM_ENCRYPTED")
            
            try {
                // Si el body contiene 'errorCode', es un error del servidor, no lo tocamos
                if (bodyString.contains("errorCode")) {
                    return@Converter delegate.convert(bodyString.toResponseBody(value.contentType()))
                }

                val wrapper = qsm.json.decodeFromString<QuantumEncryptedResponse>(bodyString)
                val data = wrapper.encryptedResponse
                
                if (data != null) {
                    val secret = qsm.getSecretSync() 
                        ?: throw Exception("Seguridad: No hay sesión cuántica activa")
                    
                    val decryptedJson = qsm.decrypt(data, secret)
                    uiTayLog(decryptedJson, "QUANTUM_DECRYPTED")
                    
                    delegate.convert(decryptedJson.toResponseBody(value.contentType()))
                } else {
                    delegate.convert(bodyString.toResponseBody(value.contentType()))
                }
            } catch (e: Exception) {
                // Si falla el descifrado de algo que DEBERÍA estar cifrado, lanzamos el error
                // para que no llegue basura al mapeador de Gson
                if (bodyString.contains("encryptedResponse")) {
                    throw Exception("Fallo crítico al descifrar respuesta: ${e.message}")
                }
                delegate.convert(bodyString.toResponseBody(value.contentType()))
            }
        }
    }
}
