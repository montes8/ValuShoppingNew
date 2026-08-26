package com.tayler.repository.utils

import android.util.Log
import com.tayler.repository.BuildConfig

/**
 * Utilidad de logs personalizada para el monitoreo de red y seguridad.
 */
fun String.uiTayLog(tag: String = "TayLog") {
    if (BuildConfig.DEBUG) {
        Log.d(tag, "---------------------------------\n $this \n---------------------------------\n")
    }
}
