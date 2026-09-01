package com.tayler.core.network.data.api.model

import com.google.gson.annotations.SerializedName
import com.tayler.core.model.CategoryModel
import com.tayler.core.model.ImageMoreModel
import com.tayler.core.common.utils.EMPTY_VALE

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
){
    companion object{
        fun toModel(data : ImageMoreResponse) =
            ImageMoreModel(
                uid = data.uid?: EMPTY_VALE,
                name = data.name?: EMPTY_VALE,
                idProduct = data.idProduct?: EMPTY_VALE,
                idUser = data.idUser?: EMPTY_VALE,
                url = data.url?: EMPTY_VALE,
                nameFile = data.nameFile?: EMPTY_VALE
            )

        fun toList(data : List<ImageMoreResponse>) = data.map {
            toModel(it)
        }


    }
}