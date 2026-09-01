package com.tayler.core.database.preferences

import com.tayler.core.model.ParamModel
import com.tayler.core.model.UserModel
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