package com.tayler.entity

import kotlinx.serialization.Serializable

@Serializable
data class ImageMoreModel (
    val uid: String,
    val name: String,
    val idProduct: String,
    val idUser: String,
    val url: String,
    val nameFile: String
)
