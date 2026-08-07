package com.tayler.valushopping.entity

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.tayler.entity.CategoryModel
import com.tayler.entity.ParamModel
import com.tayler.entity.UserModel
import com.tayler.valushopping.utils.TY_DEFAULT
import com.tayler.valushopping.utils.color_principal_amber
import com.tayler.valushopping.utils.color_principal_amber_bg
import com.tayler.valushopping.utils.color_principal_black
import com.tayler.valushopping.utils.color_principal_black_bg
import com.tayler.valushopping.utils.color_principal_blue
import com.tayler.valushopping.utils.color_principal_blue_bg
import com.tayler.valushopping.utils.color_principal_brown
import com.tayler.valushopping.utils.color_principal_brown_bg
import com.tayler.valushopping.utils.color_principal_cyan
import com.tayler.valushopping.utils.color_principal_cyan_bg
import com.tayler.valushopping.utils.color_principal_deep_orange
import com.tayler.valushopping.utils.color_principal_deep_orange_bg
import com.tayler.valushopping.utils.color_principal_green
import com.tayler.valushopping.utils.color_principal_green_bg
import com.tayler.valushopping.utils.color_principal_grey
import com.tayler.valushopping.utils.color_principal_grey_bg
import com.tayler.valushopping.utils.color_principal_indigo
import com.tayler.valushopping.utils.color_principal_indigo_bg
import com.tayler.valushopping.utils.color_principal_light_green
import com.tayler.valushopping.utils.color_principal_light_green_bg
import com.tayler.valushopping.utils.color_principal_lime
import com.tayler.valushopping.utils.color_principal_lime_bg
import com.tayler.valushopping.utils.color_principal_orange
import com.tayler.valushopping.utils.color_principal_orange_bg
import com.tayler.valushopping.utils.color_principal_pink
import com.tayler.valushopping.utils.color_principal_pink_bg
import com.tayler.valushopping.utils.color_principal_purple
import com.tayler.valushopping.utils.color_principal_purple_bg
import com.tayler.valushopping.utils.color_principal_red
import com.tayler.valushopping.utils.color_principal_red_bg
import com.tayler.valushopping.utils.color_principal_sky_blue
import com.tayler.valushopping.utils.color_principal_sky_blue_bg
import com.tayler.valushopping.utils.color_principal_teal
import com.tayler.valushopping.utils.color_principal_teal_bg
import com.tayler.valushopping.utils.color_principal_yellow
import com.tayler.valushopping.utils.color_principal_yellow_bg
import com.tayler.valushopping.utils.color_secondary_amber
import com.tayler.valushopping.utils.color_secondary_black
import com.tayler.valushopping.utils.color_secondary_blue
import com.tayler.valushopping.utils.color_secondary_brown
import com.tayler.valushopping.utils.color_secondary_cyan
import com.tayler.valushopping.utils.color_secondary_deep_orange
import com.tayler.valushopping.utils.color_secondary_green
import com.tayler.valushopping.utils.color_secondary_grey
import com.tayler.valushopping.utils.color_secondary_indigo
import com.tayler.valushopping.utils.color_secondary_light_green
import com.tayler.valushopping.utils.color_secondary_lime
import com.tayler.valushopping.utils.color_secondary_orange
import com.tayler.valushopping.utils.color_secondary_pink
import com.tayler.valushopping.utils.color_secondary_purple
import com.tayler.valushopping.utils.color_secondary_red
import com.tayler.valushopping.utils.color_secondary_sky_blue
import com.tayler.valushopping.utils.color_secondary_teal
import com.tayler.valushopping.utils.color_secondary_yellow
import com.valu.uitaycompose.utils.UI_EMPTY
import com.valu.uitaycompose.utils.extension.getNameBackgroundCustom
import com.valu.uitaycompose.utils.extension.getNameSplashCustom
import com.valu.uitaycompose.utils.extension.getNameToolbarCustom
import com.valu.uitaycompose.utils.extension.uiTayFormatTwelveHour

object AppDataVale {
    var paramData: ParamModel = ParamModel()
    var user: UserModel = UserModel()
    var latitude : String = TY_DEFAULT
    var longitude : String = TY_DEFAULT
    var categories : List<CategoryModel> = ArrayList()
    var categoriesAll : List<CategoryModel> = ArrayList()

    fun getColorPrincipal():Triple<Color,Color,Color>{
        return when(paramData.styleValu){
            "0"->{
                Triple(color_principal_pink,color_secondary_pink,color_principal_pink_bg)
            }
            "1"->{
                Triple(color_principal_red,color_secondary_red,color_principal_red_bg)
            }
            "2"->{
                Triple(color_principal_purple,color_secondary_purple,color_principal_purple_bg)
            }
            "3"->{
                Triple(color_principal_indigo,color_secondary_indigo,color_principal_indigo_bg)
            }
            "4"->{
                Triple(color_principal_blue,color_secondary_blue,color_principal_blue_bg)
            }
            "5"->{
                Triple(color_principal_sky_blue,color_secondary_sky_blue,color_principal_sky_blue_bg)
            }
            "6"->{
                Triple(color_principal_cyan,color_secondary_cyan,color_principal_cyan_bg)
            }
            "7"->{
                Triple(color_principal_teal,color_secondary_teal,color_principal_teal_bg)
            }
            "8"->{
                Triple(color_principal_green,color_secondary_green,color_principal_green_bg)
            }
            "9"->{
                Triple(color_principal_light_green,color_secondary_light_green,color_principal_light_green_bg)
            }
            "10"->{
                Triple(color_principal_lime,color_secondary_lime,color_principal_lime_bg)
            }
            "11"->{
                Triple(color_principal_yellow,color_secondary_yellow,color_principal_yellow_bg)
            }
            "12"->{
                Triple(color_principal_amber,color_secondary_amber,color_principal_amber_bg)
            }
            "13"->{
                Triple(color_principal_orange,color_secondary_orange,color_principal_orange_bg)
            }
            "14"->{
                Triple(color_principal_deep_orange,color_secondary_deep_orange,color_principal_deep_orange_bg)
            }
            "15"->{
                Triple(color_principal_brown,color_secondary_brown,color_principal_brown_bg)
            }
            "16"->{
                Triple(color_principal_grey,color_secondary_grey,color_principal_grey_bg)
            }
            "17"->{
                Triple(color_principal_black,color_secondary_black,color_principal_black_bg)
            }
            else->{
                Triple(color_principal_pink,color_principal_pink,color_principal_pink_bg)
            }
        }
    }
    fun mapperDialogText(): String {
        return "Nuestros horario de atencion es de " +
                "${paramData.hourStart?.uiTayFormatTwelveHour()} a " +
                "${paramData.hourEnd?.uiTayFormatTwelveHour()}, gracias por su comprensión."
    }

    fun getUrlBgSplash(context: Context): String{
        return if (paramData.bgService){
            "${paramData.urlImage}uploads/banners/${context.getNameSplashCustom()}/${context.getNameSplashCustom()}.png"
        }else{
            UI_EMPTY
        }
    }

    fun getUrlBg(context: Context): String{
        return if (paramData.bgService){
            "${paramData.urlImage}uploads/banners/${context.getNameBackgroundCustom()}/${context.getNameBackgroundCustom()}.png"
        }else{
            UI_EMPTY
        }
    }

    fun getUrlBgToolbar(context: Context): String{
        return if (paramData.bgService){
            "${paramData.urlImage}uploads/banners/${context.getNameToolbarCustom()}/" +
                    "${context.getNameToolbarCustom()}.png"
        }else{
            UI_EMPTY
        }
    }

}