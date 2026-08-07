package com.tayler.repository.preferences

import com.tayler.entity.ParamModel
import com.tayler.entity.UserModel
import com.tayler.repository.network.model.response.UserResponse


interface IAppPreferences {
    fun saveToken(value: String)
    fun getToken(): Boolean
    fun saveUUID(value: String)
    fun getUUID(): String
    fun saveUser(value: UserModel): UserModel
    fun getUser(): UserModel
    fun saveParaDb(value: ParamModel): ParamModel
    fun getParaDb(): ParamModel

}