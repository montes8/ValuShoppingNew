package com.tayler.valushopping.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.tayler.entity.ParamModel
import com.tayler.entity.exception.UiTayApiException
import com.tayler.entity.exception.MyNetworkException
import com.tayler.entity.exception.OutOfHour
import com.tayler.entity.exception.UnAuthorizedException
import com.tayler.valushopping.BuildConfig
import com.tayler.valushopping.R
import com.tayler.valushopping.entity.AppDataVale
import com.valu.uitaycompose.utils.HOUR_END_DEFAULT
import com.valu.uitaycompose.utils.HOUR_START_DEFAULT
import com.valu.uitaycompose.utils.UI_EMPTY
import com.valu.uitaycompose.utils.extension.uiTayShowToast
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


fun Throwable.mapperError(context: Context, appDataVale: AppDataVale): Triple<Int, String, String> {
    return when (this) {
        is MyNetworkException -> Triple(
            R.drawable.ic_error_red,
            context.getString(R.string.error_text_connection) ,
            context.getString(R.string.error_message_connection)
        )

        is UnAuthorizedException -> Triple(
            R.drawable.ic_info_error,
            context.getString(R.string.error_text_expire),
            context.getString(R.string.error_message_expire)
        )

        is UiTayApiException -> Triple(R.drawable.ic_info_error, title, messageApi)
        is OutOfHour -> Triple(
            R.drawable.ic_info_error,
            context.getString(R.string.error_text_hour) ,
            appDataVale.mapperDialogText()
        )

        else -> Triple(R.drawable.ic_info_error,
            context.getString(R.string.error_text_generic) ,
            context.getString(R.string.error_message_generic) )
    }
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

fun setImageMenu(appDataVale: AppDataVale):Int{
    val image = when(appDataVale.paramData.styleValu){
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

fun setImageLogout(appDataVale: AppDataVale):Int{
    val image = when(appDataVale.paramData.styleValu){
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

fun getDrawableResId(iconName: String?): Int {
    return when (iconName) {
        "ic_home" -> R.drawable.ic_home
        "ic_param" -> R.drawable.ic_param
        "ic_product" -> R.drawable.ic_product
        "ic_support" -> R.drawable.ic_support
        "ic_about" -> R.drawable.ic_about
        "ic_clothes" -> R.drawable.ic_clothes
        "ic_admin" -> R.drawable.ic_admin
        "ic_category" -> R.drawable.ic_category
        "ic_profile" -> R.drawable.ic_profile
        else -> R.drawable.ic_home
    }
}

fun Context.sharedImageViewFromBitmap(bitmap: Bitmap) {
    try {
        val fileShared = uiCreatePictureFile()
        val url = uiTaySaveImg(fileShared, bitmap, PATH_IMAGE_SHARED)

        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(
                Intent.EXTRA_STREAM,
                FileProvider.getUriForFile(this@sharedImageViewFromBitmap, "${BuildConfig.APPLICATION_ID}.provider", File(url))
            )
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        this.startActivity(Intent.createChooser(intent, this.getString(R.string.text_shared_product)))
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun Context.uiCreatePictureFile(nameFile: String = "imgSave"): File {
    val storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)// NOSONAR
        ?: throw IllegalStateException("External storage is not available")

    val newPath = File(storageDir, nameFile)
    if (!newPath.exists()) {
        newPath.mkdirs()
    }
    return newPath
}

fun Context.uiTaySaveImg(
    nameFile: File, img: Bitmap, nameImage: String,
    toast: Boolean = false, message: String = UI_EMPTY,
): String {
    val myPath = File(nameFile, "$nameImage.jpg")

    val fos: FileOutputStream?
    try {
        fos = FileOutputStream(myPath)
        img.compress(Bitmap.CompressFormat.JPEG, 10, fos)
        fos.flush()
        if (toast) this.uiTayShowToast(message)
    } catch (ex: FileNotFoundException) {
        ex.printStackTrace()
        if (toast) this.uiTayShowToast(R.string.error_archive)
    } catch (ex: IOException) {
        ex.printStackTrace()
        if (toast) this.uiTayShowToast(R.string.error_archive)
    }
    return myPath.absolutePath
}

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