package com.tayler.core.network.protocol

import com.tayler.core.model.QuantumPublicKeyResponse

fun interface IQuantumNetwork {
    suspend fun getQuantumPublicKey(): QuantumPublicKeyResponse
}
