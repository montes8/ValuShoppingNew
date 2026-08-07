package com.tayler.valushopping.utils

import android.content.Context
import android.content.Intent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.tayler.entity.ProductModel
import com.tayler.valushopping.R
import com.tayler.valushopping.entity.AppDataVale
import com.valu.uitaycompose.utils.COUNTRY_CODE_PE
import com.valu.uitaycompose.utils.SIZE_BIG
import com.valu.uitaycompose.utils.SIZE_MEDIUM
import com.valu.uitaycompose.utils.SIZE_NORMAL
import com.valu.uitaycompose.utils.SIZE_REGULAR
import com.valu.uitaycompose.utils.extension.uiTayConverter
import com.valu.uitaycompose.utils.extension.uiTayDeg2rad
import com.valu.uitaycompose.utils.extension.uiTayExistApplicationInDevice
import com.valu.uitaycompose.utils.extension.uiTayIsWhatsAppInstalled
import com.valu.uitaycompose.utils.extension.uiTayRad2deg
import com.valu.uitaycompose.utils.extension.uiTayShowToast
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin


fun ProductModel.distance(unit: String): Double {
    val longitudeSend = this.longitude.toDouble()
    val latitudeSend = this.latitude.toDouble()
    val theta =  longitudeSend - AppDataVale.longitude.toDouble()
    var dist =
        sin(uiTayDeg2rad(latitudeSend)) * sin(uiTayDeg2rad(AppDataVale.latitude.toDouble())) +
                cos(uiTayDeg2rad(latitudeSend)) * cos(uiTayDeg2rad(AppDataVale.latitude.toDouble())) * cos(
            uiTayDeg2rad(theta)
        )
    dist = acos(dist)
    dist = uiTayRad2deg(dist)
    dist *= 60 * 1.1515
    dist *= 1.609344
    return if (unit == "K")dist else dist.uiTayConverter()
}

fun mapperHeight(type : String): Dp {
    return when(type){
        SIZE_NORMAL -> 140.dp
        SIZE_REGULAR -> 160.dp
        SIZE_MEDIUM -> 180.dp
        SIZE_BIG -> 200.dp
        else -> 140.dp
    }
}

fun Context.openWhatsApp(phone: String, text: String,code : String = COUNTRY_CODE_PE) {
    if (existWhatsAppInDevice(this)) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                "$URL_WHATS_APP_CUSTOM$code$phone&text=$text".toUri()
            )
        )
    } else {
        this.uiTayShowToast(R.string.error_not_install)
    }
}

fun existWhatsAppInDevice(context: Context): Boolean {
    return uiTayIsWhatsAppInstalled(context, PACKAGE_APP_WHATS_APP)
            || uiTayIsWhatsAppInstalled(context, PACKAGE_APP_WHATS_APP_BUSINESS)|| uiTayExistApplicationInDevice(context, PACKAGE_APP_WHATS_APP)
            || uiTayExistApplicationInDevice(context, PACKAGE_APP_WHATS_APP_BUSINESS)
}
