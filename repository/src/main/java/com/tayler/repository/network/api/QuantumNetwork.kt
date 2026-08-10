package com.tayler.repository.network.api

import com.tayler.entity.QuantumPublicKeyResponse
import com.tayler.repository.network.ServiceApi
import com.tayler.repository.network.base.BaseNetwork
import com.tayler.repository.network.protocol.IQuantumNetwork
import com.tayler.repository.utils.processResponse
import javax.inject.Inject

class QuantumNetwork @Inject constructor(
    private val serviceApi: ServiceApi,
    private val base: BaseNetwork
) : IQuantumNetwork {

    override suspend fun getQuantumPublicKey(): QuantumPublicKeyResponse {
        return serviceApi.getQuantumPublicKey().processResponse { it }
    }
}
