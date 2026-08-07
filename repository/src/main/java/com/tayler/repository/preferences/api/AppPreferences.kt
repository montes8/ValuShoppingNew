package com.tayler.repository.preferences.api

import com.google.gson.Gson
import com.tayler.entity.ParamModel
import com.tayler.entity.UserModel
import com.tayler.repository.preferences.IAppPreferences
import com.tayler.repository.preferences.manager.PreferencesManager
import com.tayler.repository.utils.PREFERENCE_PARAM
import com.tayler.repository.utils.PREFERENCE_TOKEN
import com.tayler.repository.utils.PREFERENCE_USER
import com.tayler.repository.utils.PREFERENCE_UUID
import javax.inject.Inject

class AppPreferences @Inject constructor(private val preferenceManager: PreferencesManager) :
    IAppPreferences {

    override fun saveToken(value: String) = preferenceManager.setValue(PREFERENCE_TOKEN, value)

    override fun getToken() = preferenceManager.getString(PREFERENCE_TOKEN).isNotEmpty()
    override fun saveUUID(value: String)= preferenceManager.setValue(PREFERENCE_UUID, value)

    override fun getUUID() = preferenceManager.getString(PREFERENCE_UUID)

    override fun saveUser(value: UserModel): UserModel {
        val userDataAsString = Gson().toJson(value)
        preferenceManager.setValue(PREFERENCE_USER, userDataAsString)
        return getUser()
    }

    override fun getUser(): UserModel {
        return retrieveSavedString().toUserModel()
    }

    override fun saveParaDb(value: ParamModel): ParamModel{
        val paramDataAsString = Gson().toJson(value)
        preferenceManager.setValue(PREFERENCE_PARAM, paramDataAsString)
        return getParaDb()
    }

    override fun getParaDb(): ParamModel {
        return retrieveSavedStringParam().toParamModel()
    }

    private fun String.toParamModel(): ParamModel {
        return try {
            Gson().fromJson(this, ParamModel::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            ParamModel()
        }
    }

    private fun String.toUserModel(): UserModel {
        return try {
            Gson().fromJson(this, UserModel::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            UserModel()
        }
    }

    private fun retrieveSavedString() = preferenceManager.getString(PREFERENCE_USER)

    private fun retrieveSavedStringParam() = preferenceManager.getString(PREFERENCE_PARAM)

}