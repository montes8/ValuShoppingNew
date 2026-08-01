package com.tayler.repository.network.model.response

import com.google.gson.annotations.SerializedName
import com.tayler.repository.utils.EMPTY_VALE

data class UserResponse(
    @SerializedName("uid")
    var uid: String? = EMPTY_VALE,
    @SerializedName("nameUser")
    var nameUser: String? = EMPTY_VALE,
    @SerializedName("names")
    var names: String? = EMPTY_VALE,
    @SerializedName("lastName")
    var lastName: String? = EMPTY_VALE,
    @SerializedName("document")
    var document: String? = EMPTY_VALE,
    @SerializedName("email")
    var email: String? = EMPTY_VALE,
    @SerializedName("phone")
    var phone: String? = EMPTY_VALE,
    @SerializedName("address")
    var address: String? = EMPTY_VALE,
    @SerializedName("img")
    var img: String? = EMPTY_VALE,
    @SerializedName("imgBanner")
    var imgBanner: String? = EMPTY_VALE,
    @SerializedName("rol")
    var rol: String? = EMPTY_VALE,
    @SerializedName("deliveryPoint")
    var deliveryPoint: String? = EMPTY_VALE,
    @SerializedName("district")
    var district: String? = EMPTY_VALE,
    @SerializedName("countryCode")
    var countryCode: String? = "PE",
    @SerializedName("latitude")
    var latitude: String? = "0",
    @SerializedName("longitude")
    var longitude: String? = "0",
    @SerializedName("limitDistance")
    var limitDistance: String? = "0",
    @SerializedName("limitProductAdd")
    var limitProductAdd: String? = "0",
    @SerializedName("addMoreImage")
    var addMoreImage: Boolean? = false,
    @SerializedName("addPrincipal")
    var addPrincipal: Boolean? = false,
    @SerializedName("sellerClient")
    var sellerClient: String? = EMPTY_VALE
)