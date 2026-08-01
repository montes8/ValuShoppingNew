package com.tayler.valushopping.utils

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.tayler.repository.ErrorAuthorization
import com.tayler.repository.ErrorNetwork
import com.tayler.repository.ExceptionMapper
import com.tayler.repository.ExceptionMapperSoap


fun Context.validNetWork() = isConnected() && !isAirplaneModeActive()

fun Throwable.mapperError(): String{
    return when (this) {
        is ExceptionMapper -> {
            this.apiException.errorMessage
        }
        is ExceptionMapperSoap ->{
            Log.d("errorsoat",this.apiExceptionSoap)
            this.apiExceptionSoap
        }


        is ErrorAuthorization -> {
            "Error de autenticacion"
        }

        is ErrorNetwork -> {
            "Error de conexion de internet"
        }

        else -> "Error  Generic"
    }
}

fun  String.mapperLog() = "$LINE_SEPARATOR $this"



fun isNightModeEnabled(): Boolean {
    return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
}

