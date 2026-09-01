package com.tayler.core.network.api

import com.tayler.core.model.QuantumPublicKeyResponse
import com.tayler.core.network.api.ServiceApi
import com.tayler.core.network.base.BaseNetwork
import com.tayler.core.network.protocol.IQuantumNetwork
import com.tayler.core.network.utils.processResponse
import javax.inject.Inject

class QuantumNetwork @Inject constructor(
    private val serviceApi: ServiceApi,
    private val base: BaseNetwork
) : IQuantumNetwork {

    override suspend fun getQuantumPublicKey(): QuantumPublicKeyResponse {
        return serviceApi.getQuantumPublicKey().processResponse { it }
    }
}
