package com.tayler.repository.network.model.request

import com.google.gson.annotations.SerializedName
import com.tayler.entity.ProductImageModel
import com.tayler.repository.utils.EMPTY_VALE

data class ProductImageRequest(
    @SerializedName("name")
    val name: String? = EMPTY_VALE,
    @SerializedName("idProduct")
    val idProduct: String? = EMPTY_VALE,
    @SerializedName("idUser")
    val idUser: String? = EMPTY_VALE,
    @SerializedName("url")
    val url: String? = EMPTY_VALE,
    @SerializedName("nameFile")
    val nameFile: String? = EMPTY_VALE
){
    companion object{
        fun toModel(data : ProductImageModel) =
            ProductImageRequest(
                name = data.name,
                idProduct = data.idProduct,
                idUser = data.idUser,
                url = data.url,
                nameFile = data.nameFile,
            )
    }
}