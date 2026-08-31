package com.tayler.ui

import com.valu.uitaycompose.utils.COUNTRY_AR
import com.valu.uitaycompose.utils.COUNTRY_CODE_AR
import com.valu.uitaycompose.utils.COUNTRY_CODE_MX
import com.valu.uitaycompose.utils.COUNTRY_CODE_PE
import com.valu.uitaycompose.utils.COUNTRY_MX

fun String.mapperCodeSocial():String{
    return when(this){
        COUNTRY_AR ->{COUNTRY_CODE_AR}
        COUNTRY_MX ->{COUNTRY_CODE_MX}
        else ->{COUNTRY_CODE_PE}
    }
}
