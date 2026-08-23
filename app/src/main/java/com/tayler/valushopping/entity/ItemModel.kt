package com.tayler.valushopping.entity

import com.google.gson.annotations.SerializedName
import com.valu.uitaycompose.utils.UI_EMPTY
import kotlinx.serialization.Serializable

@Serializable
data class ItemModel(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("title")
    val title: String = UI_EMPTY,

    @SerializedName("icon")
    val icon: String = "ic_home"
)
