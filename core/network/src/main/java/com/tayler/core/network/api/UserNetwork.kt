package com.tayler.core.network.api

import com.tayler.core.model.ParamModel
import com.tayler.core.network.api.ServiceApi
import com.tayler.core.network.base.BaseNetwork
import com.tayler.core.network.data.api.model.ParamResponse
import com.tayler.core.network.protocol.IUserNetwork
import com.tayler.core.common.utils.EMPTY_VALE
import com.tayler.core.network.utils.processResponse
import javax.inject.Inject

class UserNetwork @Inject constructor(
    private val serviceApi: ServiceApi,
    private val base: BaseNetwork
) : IUserNetwork {

    override suspend fun loadParam(id: String) = base.safeApiCall {
        serviceApi.loadParam(id).processResponse { ParamResponse.toModel(it) }
    }
}
