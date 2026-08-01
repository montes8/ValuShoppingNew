package com.tayler.repository.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun String.uiTayFormatTwelveHour():String{
    return try {
        val formatHour = SimpleDateFormat("hh:mm:ss")
        val hourCurrent: Date = formatHour.parse(this) as Date
        val converter = SimpleDateFormat("hh:mm a")
        converter.format(hourCurrent)
    } catch (e: ParseException) {
        ""
    }
}

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