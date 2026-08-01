package com.tayler.repository.network.protocol

interface IUserNetwork {
    suspend fun loadParam(id:String): ParamModel

    suspend fun login(user: String, key: String): LoginModel

    suspend fun saveParam(param: ParamModel): ParamModel

    suspend fun updateParam(param: ParamModel): ParamModel
}