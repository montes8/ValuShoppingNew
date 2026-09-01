package com.tayler.core.database.preferences

import com.tayler.core.common.utils.PREFERENCE_PARAM
import com.tayler.core.common.utils.PREFERENCE_TOKEN
import com.tayler.core.common.utils.PREFERENCE_USER
import com.tayler.core.common.utils.PREFERENCE_UUID
import com.tayler.core.common.utils.toJson
import com.tayler.core.common.utils.toModel
import com.tayler.core.model.ParamModel
import com.tayler.core.model.UserModel
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
