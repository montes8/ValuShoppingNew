package com.tayler.repository.preferences.api

import com.tayler.entity.ParamModel
import com.tayler.entity.UserModel
import com.tayler.repository.preferences.IAppPreferences
import com.tayler.repository.preferences.manager.PreferencesManager
import com.tayler.repository.utils.PREFERENCE_PARAM
import com.tayler.repository.utils.PREFERENCE_TOKEN
import com.tayler.repository.utils.PREFERENCE_USER
import com.tayler.repository.utils.PREFERENCE_UUID
import com.tayler.repository.utils.toModel
import com.tayler.repository.utils.toJson
import javax.inject.Inject

class AppPreferences @Inject constructor(private val preferenceManager: PreferencesManager) :
    IAppPreferences {

    override fun saveToken(value: String) = preferenceManager.setValue(PREFERENCE_TOKEN, value)

    override fun getToken() = preferenceManager.getString(PREFERENCE_TOKEN).isNotEmpty()

    override fun saveUUID(value: String) = preferenceManager.setValue(PREFERENCE_UUID, value)

    override fun getUUID() = preferenceManager.getString(PREFERENCE_UUID)

    override fun saveUser(value: UserModel): UserModel {
        preferenceManager.setValue(PREFERENCE_USER, value.toJson())
        return getUser()
    }

    override fun getUser(): UserModel {
        return preferenceManager.getString(PREFERENCE_USER).toModel<UserModel>() ?: UserModel()
    }

    override fun saveParaDb(value: ParamModel): ParamModel {
        preferenceManager.setValue(PREFERENCE_PARAM, value.toJson())
        return getParaDb()
    }

    override fun getParaDb(): ParamModel {
        return preferenceManager.getString(PREFERENCE_PARAM).toModel<ParamModel>() ?: ParamModel()
    }
}
