package com.tayler.repository.network.protocol

import com.tayler.entity.QuantumPublicKeyResponse

interface IQuantumNetwork {
    suspend fun getQuantumPublicKey(): QuantumPublicKeyResponse
}
