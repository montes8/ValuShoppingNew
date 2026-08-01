package com.tayler.repository.network.model.response

import com.google.gson.annotations.SerializedName
import com.tayler.repository.utils.EMPTY_VALE

data class ProductResponse(
    @SerializedName("uid")
    var uid: String? = EMPTY_VALE,
    @SerializedName("name")
    var name: String? = EMPTY_VALE,
    @SerializedName("description")
    var description: String? = EMPTY_VALE,
    @SerializedName("type")
    var type: String? = "0",
    @SerializedName("category")
    var category: String? = "0",
    @SerializedName("price")
    var price: String? = EMPTY_VALE,
    @SerializedName("priceTwo")
    var priceTwo: String? = EMPTY_VALE,
    @SerializedName("state")
    var state: Boolean? = false,
    @SerializedName("img")
    var img: String? = EMPTY_VALE,
    @SerializedName("url")
    var url: String? = EMPTY_VALE,
    @SerializedName("urlBanner")
    var urlBanner: String? = EMPTY_VALE,
    @SerializedName("gender")
    var gender: String? = "0",
    @SerializedName("phone")
    var phone: String? = EMPTY_VALE,
    @SerializedName("principal")
    var principal: Boolean? = false,
    @SerializedName("admin")
    var admin: Boolean? = false,
    @SerializedName("idUser")
    var idUser: String? = EMPTY_VALE,
    @SerializedName("sizeHeight")
    var sizeHeight: String? = "N",
    @SerializedName("countryCode")
    var countryCode: String? = EMPTY_VALE,
    @SerializedName("deliveryPoint")
    var deliveryPoint: String? = EMPTY_VALE,
    @SerializedName("district")
    var district: String? = EMPTY_VALE,
    @SerializedName("nameFile")
    var nameFile: String? = EMPTY_VALE,
    @SerializedName("latitude")
    var latitude: String? = "0",
    @SerializedName("longitude")
    var longitude: String? =  "0",
    @SerializedName("limitDistance")
    var limitDistance: String? = "5",
    @SerializedName("banner")
    var banner: Boolean? = false,
    @SerializedName("linkBanner")
    var linkBanner: String? = EMPTY_VALE,
    @SerializedName("click")
    var click: Boolean? = true,
    @SerializedName("stateNew")
    var stateNew: Boolean? = true,
    @SerializedName("sellerClient")
    var sellerClient: String? = EMPTY_VALE,
)