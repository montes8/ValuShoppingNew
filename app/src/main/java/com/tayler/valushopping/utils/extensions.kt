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

fun setImageMenu():Int{
    val image = when(AppDataVale.styleValu){
        "0"->{
            R.drawable.ic_menu_home
        }
        "1"->{R.drawable.ic_menu_home_1}
        "2"->{R.drawable.ic_menu_home_2}
        "3"->{R.drawable.ic_menu_home_3}
        "4"->{R.drawable.ic_menu_home_4}
        "5"->{R.drawable.ic_menu_home_5}
        "6"->{R.drawable.ic_menu_home_6}
        "7"->{R.drawable.ic_menu_home_7}
        "8"->{R.drawable.ic_menu_home_8}
        "9"->{R.drawable.ic_menu_home_9}
        "10"->{R.drawable.ic_menu_home_10}
        "11"->{R.drawable.ic_menu_home_11}
        "12"->{R.drawable.ic_menu_home_12}
        "13"->{R.drawable.ic_menu_home_13}
        "14"->{R.drawable.ic_menu_home_14}
        "15"->{R.drawable.ic_menu_home_15}
        "16"->{R.drawable.ic_menu_home_16}
        "17"->{R.drawable.ic_menu_home_17}
        else->{
            R.drawable.ic_menu_home
        }
    }
    return image
}

fun setImageLogout():Int{
    val image = when(AppDataVale.styleValu){
        "0"->{
            R.drawable.ic_logout
        }
        "1"->{R.drawable.ic_logout_1}
        "2"->{R.drawable.ic_logout_2}
        "3"->{R.drawable.ic_logout_3}
        "4"->{R.drawable.ic_logout_4}
        "5"->{R.drawable.ic_logout_5}
        "6"->{R.drawable.ic_logout_6}
        "7"->{R.drawable.ic_logout_7}
        "8"->{R.drawable.ic_logout_8}
        "9"->{R.drawable.ic_logout_9}
        "10"->{R.drawable.ic_logout_10}
        "11"->{R.drawable.ic_logout_11}
        "12"->{R.drawable.ic_logout_12}
        "13"->{R.drawable.ic_logout_13}
        "14"->{R.drawable.ic_logout_14}
        "15"->{R.drawable.ic_logout_15}
        "16"->{R.drawable.ic_logout_16}
        "17"->{R.drawable.ic_logout_17}
        else->{
            R.drawable.ic_logout
        }
    }
    return image
}

