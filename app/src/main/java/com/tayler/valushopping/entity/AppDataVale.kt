package com.tayler.valushopping.entity

import com.tayler.entity.CategoryModel
import com.tayler.entity.ParamModel
import com.tayler.entity.UserModel
import com.tayler.valushopping.R
import com.tayler.valushopping.utils.TY_DEFAULT
import com.valu.uitaycompose.utils.extension.uiTayFormatTwelveHour


object AppDataVale {

    var paramData: ParamModel = ParamModel()
    var user: UserModel = UserModel()
    var latitude : String = TY_DEFAULT
    var longitude : String = TY_DEFAULT
    var styleValu : String = "0"
    var categories : List<CategoryModel> = ArrayList()
    var categoriesAll : List<CategoryModel> = ArrayList()
    var bgService : Boolean = false

    fun getColorPrincipal():Triple<Int,Int,Int>{
        return when(styleValu){
            "0"->{
                Triple(R.color.color_principal_pink,R.color.color_secondary_pink,R.color.color_principal_pink_bg)
            }
            "1"->{
                Triple(R.color.color_principal_red,R.color.color_secondary_red,R.color.color_principal_red_bg)
            }
            "2"->{
                Triple(R.color.color_principal_purple,R.color.color_secondary_purple,R.color.color_principal_purple_bg)
            }
            "3"->{
                Triple(R.color.color_principal_indigo,R.color.color_secondary_indigo,R.color.color_principal_indigo_bg)
            }
            "4"->{
                Triple(R.color.color_principal_blue,R.color.color_secondary_blue,R.color.color_principal_blue_bg)
            }
            "5"->{
                Triple(R.color.color_principal_sky_blue,R.color.color_secondary_sky_blue,R.color.color_principal_sky_blue_bg)
            }
            "6"->{
                Triple(R.color.color_principal_cyan,R.color.color_secondary_cyan,R.color.color_principal_cyan_bg)
            }
            "7"->{
                Triple(R.color.color_principal_teal,R.color.color_secondary_teal,R.color.color_principal_teal_bg)
            }
            "8"->{
                Triple(R.color.color_principal_green,R.color.color_secondary_green,R.color.color_principal_green_bg)
            }
            "9"->{
                Triple(R.color.color_principal_light_green,R.color.color_secondary_light_green,R.color.color_principal_light_green_bg)
            }
            "10"->{
                Triple(R.color.color_principal_lime,R.color.color_secondary_lime,R.color.color_principal_lime_bg)
            }
            "11"->{
                Triple(R.color.color_principal_yellow,R.color.color_secondary_yellow,R.color.color_principal_yellow_bg)
            }
            "12"->{
                Triple(R.color.color_principal_amber,R.color.color_secondary_amber,R.color.color_principal_amber_bg)
            }
            "13"->{
                Triple(R.color.color_principal_orange,R.color.color_secondary_orange,R.color.color_principal_orange_bg)
            }
            "14"->{
                Triple(R.color.color_principal_deep_orange,R.color.color_secondary_deep_orange,R.color.color_principal_deep_orange_bg)
            }
            "15"->{
                Triple(R.color.color_principal_brown,R.color.color_secondary_brown,R.color.color_principal_brown_bg)
            }
            "16"->{
                Triple(R.color.color_principal_grey,R.color.color_secondary_grey,R.color.color_principal_grey_bg)
            }
            "17"->{
                Triple(R.color.color_principal_black,R.color.color_secondary_black,R.color.color_principal_black_bg)
            }
            else->{
                Triple(R.color.color_principal_pink,R.color.color_principal_pink,R.color.color_principal_pink_bg)
            }
        }
    }
    fun mapperDialogText(): String {
        return "Nuestros horario de atencion es de " +
                "${paramData.hourStart?.uiTayFormatTwelveHour()} a " +
                "${paramData.hourEnd?.uiTayFormatTwelveHour()}, gracias por su comprensión."
    }
}