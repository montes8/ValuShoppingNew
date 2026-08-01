package com.tayler.repository.preferences

import com.tayler.entity.UserModel
import com.tayler.repository.network.model.response.UserResponse


interface IAppPreferences {
    fun saveToken(value: String)
    fun getToken(): Boolean

    fun saveUUID(value: String)
    fun getUUID(): String
    fun saveUser(value: UserModel): UserModel
    fun getUser(): UserModel

    fun saveStyle(value: String)
    fun getStyle(): String

    fun saveTexWelcome(value: String)
    fun getTexWelcome(): String

    fun saveBgService(value: Boolean)
    fun getBgService(): Boolean

    fun saveIdIcon(value: String)
    fun geIdIcon(): String

    fun saveIdIconOld(value: String)
    fun geIdIconOld(): String
}