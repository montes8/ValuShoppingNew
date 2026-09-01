package com.tayler.core.network.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import com.google.gson.Gson
import com.tayler.core.model.exception.GenericException
import com.tayler.core.model.exception.UiTayApiException
import com.tayler.core.model.exception.UnAuthorizedException
import com.tayler.core.network.exception.CompleteErrorModel
import kotlinx.coroutines.CancellationException
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


fun ResponseBody?.toCompleteErrorModel(code: Int): Exception {
    return this?.let {
        val bodyString = try { it.string() } catch (_: Exception) { "" }
        if (code == 407) throw UnAuthorizedException()
        
        // Si el body no parece JSON, devolvemos genérico para evitar crash de Gson
        if (!bodyString.trim().startsWith("{")) return GenericException()

        return Gson().fromJson(
            bodyString,
            CompleteErrorModel::class.java
        )?.getApiException() ?: GenericException()
    } ?: GenericException()
}

inline fun <T, R> Response<T>.processResponse(transform: (T) -> R): R {
    if (this.isSuccessful) {
        this.body()?.let {
            return transform(it)
        } ?: throw NullPointerException("Empty response body")
    } else {
        throw this.errorBody().toCompleteErrorModel(this.code())
    }
}

fun Throwable.toAppException(): Exception {
    return when (this) {
        is CancellationException -> throw this
        is UiTayApiException -> this
        is UnAuthorizedException -> this
        is IllegalArgumentException -> UiTayApiException(
            code = 0,
            title = "Error de Configuración",
            messageApi = "Error en el CertificatePinner: $message"
        )
        else -> UiTayApiException(
            code = 0,
            title = "Error",
            messageApi = message ?: "Error desconocido"
        )
    }
}
