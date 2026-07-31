package com.tayler.valushopping.utils

import androidx.lifecycle.MutableLiveData

object ValeResult {

    val eventUpdateListProduct:MutableLiveData<Boolean> by lazy{
        MutableLiveData<Boolean>()
    }
}