package com.tayler.entity

data class UserModel(
    var uid: String = "",
    var nameUser: String = "",
    var names: String = "",
    var lastName: String = "",
    var document: String = "",
    var email: String = "",
    var phone: String = "",
    var address: String = "",
    var img: String = "",
    var imgBanner: String = "",
    var rol: String = "",
    var deliveryPoint: String = "",
    var district: String = "",
    var countryCode: String = "",
    var latitude: String = "",
    var longitude: String = "",
    var limitDistance: String = "",
    var limitProductAdd: String = "",
    var addMoreImage: Boolean = false,
    var addPrincipal: Boolean = false,
    var sellerClient: String = ""
)
