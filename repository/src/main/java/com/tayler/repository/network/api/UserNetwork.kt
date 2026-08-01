package com.tayler.repository.network.api

import com.tayler.entity.DataLogin
import com.tayler.entity.ParamModel
import com.tayler.repository.network.ServiceApi
import com.tayler.repository.network.base.BaseNetwork
import com.tayler.repository.network.model.request.LoginRequest
import com.tayler.repository.network.model.response.LoginResponse
import com.tayler.repository.network.model.response.ParamResponse
import com.tayler.repository.network.protocol.IUserNetwork
import com.tayler.repository.utils.EMPTY_VALE
import com.tayler.repository.utils.toCompleteErrorModel
import com.tayler.repository.utils.validateBody
import com.tayler.repository.utils.validateData
import javax.inject.Inject

class UserNetwork @Inject constructor(
    private val serviceApi: ServiceApi,
    private val base: BaseNetwork
) :
    IUserNetwork {
    override suspend fun loadParam(id:String): ParamModel {
        return base.executeWithConnection(false) {
            var model : ParamModel ? = null
                val response = serviceApi.loadParam(id)
                if (response.validateData()) {
                    model = ParamResponse.toModel(response.validateBody())
                }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }

    override suspend fun login(user: String, key: String): DataLogin {
        return base.executeWithConnection {
            var model: DataLogin? = null
            val response = serviceApi.login(LoginRequest(user, key))
            if (response.validateData()) {
                model = LoginResponse.toModel(response.validateBody())
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }

    override suspend fun saveParam(param: ParamModel): ParamModel {
        return base.executeWithConnection {
            var model: ParamModel? = null
            val response = serviceApi.saveParam(
                ParamResponse.toModelRequest(param))
            if (response.validateData()) {
                model =  ParamResponse.toModel(response.validateBody())
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())

        }
    }

    override suspend fun updateParam(param: ParamModel): ParamModel {
        return base.executeWithConnection {
            var model: ParamModel? = null
            val response =
                serviceApi.updateParam(param.uid ?: EMPTY_VALE,
                    ParamResponse.toModelRequest(param))
            if (response.validateData()) {
                model =  ParamResponse.toModel(response.validateBody())
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }
}