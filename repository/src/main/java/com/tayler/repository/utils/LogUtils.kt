package com.tayler.repository.utils

import android.util.Log
import com.tayler.repository.BuildConfig

/**
 * Utilidad de logs personalizada para el monitoreo de red y seguridad.
 */
fun uiTayLog(value: String, tag: String = "UI_TAY_TAG") {
    if (BuildConfig.DEBUG) {
        val maxLogSize = 4000
        if (value.length > maxLogSize) {
            var i = 0
            while (i < value.length) {
                val end = kotlin.math.min(i + maxLogSize, value.length)
                Log.d(tag, value.substring(i, end))
                i = end
            }
        } else {
            Log.d(tag, value)
        }
    }
}
