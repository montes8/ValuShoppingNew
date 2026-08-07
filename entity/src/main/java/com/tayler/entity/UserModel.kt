package com.tayler.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val uid: String = "",
    val nameUser: String = "",
    val names: String = "",
    val lastName: String = "",
    val document: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val img: String = "",
    val imgBanner: String = "",
    val rol: String = "",
    val deliveryPoint: String = "",
    val district: String = "",
    val countryCode: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val limitDistance: String = "",
    val limitProductAdd: String = "",
    val addMoreImage: Boolean = false,
    val addPrincipal: Boolean = false,
    val sellerClient: String = ""
)
