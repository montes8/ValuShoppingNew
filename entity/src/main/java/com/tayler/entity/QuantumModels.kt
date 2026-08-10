package com.tayler.entity

import kotlinx.serialization.Serializable

@Serializable
data class QuantumPublicKeyResponse(
    val pubKeyBase64: String = ""
)

@Serializable
data class QuantumEncryptedResponse(
    val encryptedResponse: QuantumCipherData? = null
)

@Serializable
data class QuantumCipherData(
    val ciphertext: String = "",
    val iv: String = "",
    val authTag: String = ""
)
