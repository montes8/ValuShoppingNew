package com.tayler.repository.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import com.google.gson.Gson
import com.tayler.entity.exception.GenericException
import com.tayler.entity.exception.UnAuthorizedException
import com.tayler.repository.network.exception.CompleteErrorModel
import okhttp3.ResponseBody
import retrofit2.Response

@SuppressLint("MissingPermission")
fun Context?.isConnected(): Boolean {
    return this?.let {
        val cm = it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.getNetworkCapabilities(cm.activeNetwork)
            ?.hasCapability((NetworkCapabilities.NET_CAPABILITY_INTERNET)) ?: false
    } ?: false
}

fun Context?.isAirplaneModeActive(): Boolean {
    return this?.let {
        return Settings.Global.getInt(it.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    } ?: false
}


fun <T> Response<T>?.validateData(): Boolean {
    return this?.isSuccessful == true && this.body() != null
}

fun <T> Response<T>?.validateBody(): T {
    this?.body()?.let {
        return it
    } ?: throw NullPointerException()
}

fun ResponseBody?.toCompleteErrorModel(code: Int): Exception {
    return this?.let {
        return if (code == 407) throw UnAuthorizedException() else Gson().fromJson(
            it.string(),
            CompleteErrorModel::class.java
        )?.getApiException() ?: GenericException()
    } ?: GenericException()
}