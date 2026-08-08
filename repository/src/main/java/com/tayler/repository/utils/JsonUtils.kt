package com.tayler.repository.utils

import com.google.gson.Gson

inline fun <reified T> T.toJson(): String = Gson().toJson(this)

inline fun <reified T> String.toModel(): T? {
    return try {
        Gson().fromJson(this, T::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
