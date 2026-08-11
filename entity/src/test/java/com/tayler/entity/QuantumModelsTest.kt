package com.tayler.entity

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class QuantumModelsTest {

    @Test
    fun `QuantumPublicKeyResponse coverage`() {
        val model = QuantumPublicKeyResponse()
        assertEquals("", model.pubKeyBase64)
        
        val model2 = QuantumPublicKeyResponse("key")
        assertEquals("key", model2.pubKeyBase64)
        
        val copy = model2.copy(pubKeyBase64 = "new")
        assertEquals("new", copy.pubKeyBase64)
        assertNotEquals(model, model2)
        assertNotNull(model.hashCode())
        assertEquals(model, model.copy())
    }

    @Test
    fun `QuantumEncryptedResponse coverage`() {
        val model = QuantumEncryptedResponse()
        assertEquals(null, model.encryptedResponse)
        
        val data = QuantumCipherData("c", "i", "t")
        val model2 = QuantumEncryptedResponse(data)
        assertEquals(data, model2.encryptedResponse)
        
        val copy = model2.copy(encryptedResponse = null)
        assertEquals(null, copy.encryptedResponse)
        assertNotEquals(model.toString(), model2.toString())
    }

    @Test
    fun `QuantumCipherData coverage`() {
        val model = QuantumCipherData()
        assertEquals("", model.ciphertext)
        
        val model2 = QuantumCipherData("c", "i", "t")
        assertEquals("c", model2.ciphertext)
        assertEquals("i", model2.iv)
        assertEquals("t", model2.authTag)
        
        val copy = model2.copy(iv = "new_iv")
        assertEquals("new_iv", copy.iv)
        assertNotEquals(model2, copy)
    }
}
