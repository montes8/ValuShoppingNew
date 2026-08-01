package com.tayler.repository.network.model.response

import com.google.gson.annotations.SerializedName

data class ImageMoreResponse(
    @SerializedName("uid")
    var uid: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("idProduct")
    var idProduct: String?,
    @SerializedName("idUser")
    var idUser: String?,
    @SerializedName("url")
    var url: String?,
    @SerializedName("nameFile")
    var nameFile: String?
)