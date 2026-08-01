package com.tayler.repository.network.protocol

import com.tayler.entity.DataLogin
import com.tayler.entity.ParamModel

interface IUserNetwork {
    suspend fun loadParam(id:String): ParamModel

    suspend fun login(user: String, key: String): DataLogin

    suspend fun saveParam(param: ParamModel): ParamModel

    suspend fun updateParam(param: ParamModel): ParamModel
}