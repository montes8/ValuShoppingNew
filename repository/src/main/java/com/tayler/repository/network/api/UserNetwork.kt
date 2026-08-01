package com.tayler.repository.network.api

import com.tayler.repository.network.ServiceApi
import javax.inject.Inject

class UserNetwork @Inject constructor(
    private val serviceApi: ServiceApi,
    private val base: BaseNetwork
) :
    IUserNetwork {
    override suspend fun loadParam(id:String): ParamResponse {
        return base.executeWithConnection(false) {
            var model : ParamResponse ? = null
                val response = serviceApi.loadParam(id)
                if (response.validateData()) {
                    model = response.validateBody()
                }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }

    override suspend fun login(user: String, key: String): LoginResponse {
        return base.executeWithConnection {
            var model: LoginResponse? = null
            val response = serviceApi.login(LoginRequest(user, key))
            if (response.validateData()) {
                model = response.validateBody()
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }

    override suspend fun saveParam(param: ParamResponse): ParamResponse {
        return base.executeWithConnection {
            var model: ParamResponse? = null
            val response = serviceApi.saveParam(param)
            if (response.validateData()) {
                model = response.validateBody()
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())

        }
    }

    override suspend fun updateParam(param: ParamResponse): ParamResponse {
        return base.executeWithConnection {
            var model: ParamResponse? = null
            val response = serviceApi.updateParam(param.uid ?: EMPTY_VALE, param)
            if (response.validateData()) {
                model = response.validateBody()
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }
}