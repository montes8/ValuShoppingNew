package com.tayler.valushopping.utils

import androidx.appcompat.app.AppCompatDelegate
import com.tayler.entity.exception.ApiException
import com.tayler.entity.exception.MyNetworkException
import com.tayler.entity.exception.OutOfHour
import com.tayler.entity.exception.UnAuthorizedException
import com.tayler.valushopping.R
import com.tayler.valushopping.entity.AppDataVale


fun Throwable.mapperError(): Triple<Int, String, String> {
    return when (this) {
        is MyNetworkException -> Triple(
            R.drawable.ic_error_red,
            ERROR_TITLE_NETWORK,
            ERROR_MESSAGE_NETWORK
        )

        is UnAuthorizedException -> Triple(
            R.drawable.ic_info_error,
            ERROR_TITLE_EXPIRE,
            ERROR_MESSAGE_EXPIRE
        )

        is ApiException -> Triple(R.drawable.ic_info_error, title, messageApi)
        is OutOfHour -> Triple(
            R.drawable.ic_info_error,
            ERROR_TITLE_OF_HOUR,
            AppDataVale.mapperDialogText()
        )

        else -> Triple(R.drawable.ic_info_error, ERROR_TITLE_GENERAL, ERROR_MESSAGE_GENERAL)
    }
}


fun isNightModeEnabled(): Boolean {
    return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
}

