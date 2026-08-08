package com.tayler.repository.network.api

import com.tayler.entity.ParamModel
import com.tayler.repository.network.ServiceApi
import com.tayler.repository.network.base.BaseNetwork
import com.tayler.repository.network.model.request.LoginRequest
import com.tayler.repository.network.model.response.LoginResponse
import com.tayler.repository.network.model.response.ParamResponse
import com.tayler.repository.network.protocol.IUserNetwork
import com.tayler.repository.utils.EMPTY_VALE
import com.tayler.repository.utils.processResponse
import javax.inject.Inject

class UserNetwork @Inject constructor(
    private val serviceApi: ServiceApi,
    private val base: BaseNetwork
) : IUserNetwork {

    override suspend fun loadParam(id: String) = base.safeApiCall {
        serviceApi.loadParam(id).processResponse { ParamResponse.toModel(it) }
    }

    override suspend fun login(user: String, key: String) = base.safeApiCall {
        serviceApi.login(LoginRequest(user, key)).processResponse { LoginResponse.toModel(it) }
    }

    override suspend fun saveParam(param: ParamModel) = base.safeApiCall {
        serviceApi.saveParam(ParamResponse.toModelRequest(param)).processResponse {
            ParamResponse.toModel(it)
        }
    }

    override suspend fun updateParam(param: ParamModel) = base.safeApiCall {
        serviceApi.updateParam(param.uid ?: EMPTY_VALE, ParamResponse.toModelRequest(param))
            .processResponse { ParamResponse.toModel(it) }
    }
}
