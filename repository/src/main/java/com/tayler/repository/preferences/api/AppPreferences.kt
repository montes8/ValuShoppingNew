package com.tayler.repository.preferences.api

import com.google.gson.Gson
import com.tayler.entity.UserModel
import com.tayler.repository.preferences.IAppPreferences
import com.tayler.repository.preferences.manager.PreferencesManager
import com.tayler.repository.utils.PREFERENCE_BG_SERVICE
import com.tayler.repository.utils.PREFERENCE_ID_ICON
import com.tayler.repository.utils.PREFERENCE_ID_OLD
import com.tayler.repository.utils.PREFERENCE_STYLE
import com.tayler.repository.utils.PREFERENCE_TEXT_WELCOME
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

    override fun saveStyle(value: String)= preferenceManager.setValue(PREFERENCE_STYLE, value)

    override fun getStyle(): String  = preferenceManager.getString(PREFERENCE_STYLE)

    override fun getTexWelcome(): String  = preferenceManager.getString(PREFERENCE_TEXT_WELCOME)

    override fun saveTexWelcome(value: String)= preferenceManager.setValue(PREFERENCE_TEXT_WELCOME, value)


    override fun saveBgService(value: Boolean)= preferenceManager.setValue(PREFERENCE_BG_SERVICE, value)

    override fun getBgService(): Boolean  = preferenceManager.getBoolean(PREFERENCE_BG_SERVICE)

    override fun saveIdIcon(value: String) = preferenceManager.setValue(PREFERENCE_ID_ICON, value)

    override fun geIdIcon(): String = preferenceManager.getString(PREFERENCE_ID_ICON,"Principal")

    override fun saveIdIconOld(value: String) = preferenceManager.setValue(PREFERENCE_ID_OLD, value)

    override fun geIdIconOld(): String = preferenceManager.getString(PREFERENCE_ID_OLD,"Principal")

    private fun String.toUserModel(): UserModel {
        return try {
            Gson().fromJson(this, UserModel::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            UserModel()
        }
    }

    private fun retrieveSavedString() = preferenceManager.getString(PREFERENCE_USER)

}