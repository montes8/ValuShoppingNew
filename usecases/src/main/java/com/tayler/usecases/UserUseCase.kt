package com.tayler.usecases

import com.tayler.entity.DataLogin
import com.tayler.entity.ParamModel
import com.tayler.repository.network.protocol.IUserNetwork
import jakarta.inject.Inject

class UserUseCase @Inject constructor(
    private val userNetwork: IUserNetwork
) {

    suspend fun loadParam(id:String): ParamModel {
        return userNetwork.loadParam(id)
    }

    suspend fun login(user: String, key: String): DataLogin {
        return userNetwork.login(user, key)
    }

    suspend fun saveParam(param: ParamModel): ParamModel {
        return userNetwork.saveParam(param)
    }

    suspend fun updateParam(param: ParamModel): ParamModel {
        return userNetwork.updateParam(param)
    }
}