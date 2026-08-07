package com.tayler.usecases

import com.tayler.entity.UserModel
import com.tayler.repository.BuildConfig
import com.tayler.repository.preferences.IAppPreferences
import jakarta.inject.Inject

class AppUseCase@Inject constructor(
    private val iAppPreferences: IAppPreferences
){
    fun saveToken(value: String){
        iAppPreferences.saveToken(value)
    }
    fun getToken(): Boolean{
        return iAppPreferences.getToken()
    }

    fun saveUUID(value: String){
        iAppPreferences.saveUUID(value)
    }
    fun getUUID(): String{
        return iAppPreferences.getUUID()
    }

    fun saveUser(value: UserModel): UserModel{
        return iAppPreferences.saveUser(value)
    }
    fun getUser(): UserModel{
        return iAppPreferences.getUser()
    }

    fun saveStyle(value: String){
        iAppPreferences.saveStyle(value)

    }
    fun getStyle(): String{
        return iAppPreferences.getStyle()
    }

    fun saveTexWelcome(value: String){
        iAppPreferences.saveTexWelcome(value)

    }
    fun getTexWelcome(): String{
        return iAppPreferences.getTexWelcome()
    }

    fun saveBgService(value: Boolean){
        iAppPreferences.saveBgService(value)
    }

    fun getBgService(): Boolean{
        return iAppPreferences.getBgService()
    }

    fun saveIdIcon(value: String){
        iAppPreferences.saveIdIcon(value)
    }
    fun geIdIcon(): String{
        return iAppPreferences.geIdIcon()
    }

    fun saveIdIconOld(value: String){
        iAppPreferences.saveIdIconOld(value)
    }
    fun geIdIconOld(): String{
        return iAppPreferences.geIdIconOld()
    }

    fun urlImage(): String{
        return BuildConfig.BASE_URL
    }
}