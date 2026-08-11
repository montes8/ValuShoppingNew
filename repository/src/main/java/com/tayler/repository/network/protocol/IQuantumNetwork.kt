package com.tayler.repository.network.protocol

import com.tayler.entity.QuantumPublicKeyResponse

fun interface IQuantumNetwork {
    suspend fun getQuantumPublicKey(): QuantumPublicKeyResponse
}
