package com.tayler.valushopping.utils

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatDelegate
import com.tayler.entity.ParamModel
import com.tayler.entity.exception.ApiException
import com.tayler.entity.exception.MyNetworkException
import com.tayler.entity.exception.OutOfHour
import com.tayler.entity.exception.UnAuthorizedException
import com.tayler.valushopping.R
import com.tayler.valushopping.entity.AppDataVale
import com.valu.uitaycompose.utils.HOUR_END_DEFAULT
import com.valu.uitaycompose.utils.HOUR_START_DEFAULT
import java.text.SimpleDateFormat
import java.util.Date


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

@SuppressLint("SimpleDateFormat")
fun ParamModel.validateHourApp():Boolean{
    val dateFormat = SimpleDateFormat("HH:mm:ss")
    val date = Date()
    val hourCurrent = dateFormat.format(date).replace(":","").toInt()
    val hourStart = dateFormat.parse(this.hourStart?: HOUR_START_DEFAULT) as Date
    val hourEnd = dateFormat.parse(this.hourEnd?: HOUR_END_DEFAULT) as Date
    val hourStartParse = dateFormat.format(hourStart).replace(":","").toInt()
    val hourEndParse = dateFormat.format(hourEnd).replace(":","").toInt()
    val validStart = hourCurrent > hourStartParse
    val validEnd = hourCurrent < hourEndParse
    return validStart && validEnd
}
