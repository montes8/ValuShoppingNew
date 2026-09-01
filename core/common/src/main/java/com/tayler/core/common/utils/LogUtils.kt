package com.tayler.core.common.utils

import android.util.Log
import com.tayler.core.common.BuildConfig

/**
 * Utilidad de logs personalizada para el monitoreo de red y seguridad.
 */
fun String.uiTayLog(tag: String = "TayLog") {
    if (BuildConfig.DEBUG) {
        Log.d(tag, "---------------------------------\n $this \n---------------------------------\n")
    }
}
