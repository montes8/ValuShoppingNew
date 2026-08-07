package com.tayler.entity

import kotlinx.serialization.Serializable

@Serializable
data class ProductImageModel (
    val name: String? = "",
    val idProduct: String? = "",
    val idUser: String? = "",
    val url: String? = "",
    val nameFile: String? = ""
)
