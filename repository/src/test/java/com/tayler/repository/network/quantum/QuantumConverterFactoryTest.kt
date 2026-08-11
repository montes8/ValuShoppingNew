package com.tayler.repository.network.quantum

import com.tayler.entity.QuantumEncryptedResponse
import io.mockk.every
import io.mockk.mockk
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import javax.inject.Provider

class QuantumConverterFactoryTest {

    private lateinit var factory: QuantumConverterFactory
    private val qsm: QuantumSecurityManager = mockk()
    private val qsmProvider: Provider<QuantumSecurityManager> = Provider { qsm }
    private val retrofit: Retrofit = mockk()
    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setUp() {
        factory = QuantumConverterFactory(qsmProvider)
        every { qsm.json } returns json
    }

    @Test
    fun `converter decrypts data correctly`() {
        val type: Type = String::class.java
        val annotations = emptyArray<Annotation>()
        val delegate: Converter<ResponseBody, Any> = mockk()
        
        every { retrofit.nextResponseBodyConverter<Any>(any(), type, any()) } returns delegate
        
        @Suppress("UNCHECKED_CAST")
        val converter = factory.responseBodyConverter(type, annotations, retrofit) as Converter<ResponseBody, Any>
        
        val encryptedJson = """{"encryptedResponse": {"ciphertext": "c", "iv": "i", "authTag": "t"}}"""
        val responseBody = encryptedJson.toResponseBody("application/json".toMediaType())
        
        val mockSecret = "secret".toByteArray()
        every { qsm.getSecretSync() } returns mockSecret
        every { qsm.decrypt(any(), mockSecret) } returns "{\"clean\":\"data\"}"
        every { delegate.convert(any()) } returns "result"

        converter.convert(responseBody)

        io.mockk.verify { qsm.decrypt(match { it.ciphertext == "c" }, mockSecret) }
    }

    @Test
    fun `converter handles missing secret by falling back or throwing`() {
        val type: Type = String::class.java
        val delegate: Converter<ResponseBody, Any> = mockk()
        every { retrofit.nextResponseBodyConverter<Any>(any(), type, any()) } returns delegate
        
        @Suppress("UNCHECKED_CAST")
        val converter = factory.responseBodyConverter(type, emptyArray(), retrofit) as Converter<ResponseBody, Any>
        
        val encryptedJson = """{"encryptedResponse": {"ciphertext": "c", "iv": "i", "authTag": "t"}}"""
        val responseBody = encryptedJson.toResponseBody("application/json".toMediaType())
        
        // Simular que no hay secreto
        every { qsm.getSecretSync() } returns null
        every { delegate.convert(any()) } returns "result"

        // El converter tiene un try-catch que maneja el error y devuelve el original
        val result = converter.convert(responseBody)
        assertNotNull(result)
    }

    @Test
    fun `converter returns original if parse fails`() {
        val type: Type = String::class.java
        val delegate: Converter<ResponseBody, Any> = mockk()
        every { retrofit.nextResponseBodyConverter<Any>(any(), type, any()) } returns delegate
        
        @Suppress("UNCHECKED_CAST")
        val converter = factory.responseBodyConverter(type, emptyArray(), retrofit) as Converter<ResponseBody, Any>
        
        val plainJson = "not a json"
        val responseBody = plainJson.toResponseBody("application/json".toMediaType())
        
        every { delegate.convert(any()) } returns "result"

        converter.convert(responseBody)

        io.mockk.verify(exactly = 0) { qsm.decrypt(any(), any()) }
    }
}
