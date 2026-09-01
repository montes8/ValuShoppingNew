package com.tayler.core.database.preferences

import android.content.SharedPreferences
import com.tayler.core.common.utils.EMPTY_VALE
import javax.inject.Inject

class PreferencesManager @Inject constructor(private val preferences: SharedPreferences) {

    fun setValue(key: String, value: String) {
        preferences.edit().putString(key, value).commit()
    }

    fun setValue(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).commit()
    }

    fun setValue(key: String, value: Int) {
        preferences.edit().putInt(key, value).commit()
    }

    fun getString(key: String,valueDefault: String = EMPTY_VALE): String = preferences.getString(key, valueDefault) ?: valueDefault

    fun getBoolean(key: String): Boolean = preferences.getBoolean(key, false)

}