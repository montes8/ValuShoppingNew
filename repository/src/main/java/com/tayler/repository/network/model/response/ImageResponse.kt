package com.tayler.repository.network.model.response

import com.google.gson.annotations.SerializedName
import com.tayler.entity.ImageModel
import com.tayler.entity.ImageMoreModel
import com.tayler.repository.utils.EMPTY_VALE

data class ImageResponse(
    @SerializedName("nombre")
    var nameImage: String?
){
    companion object{
        fun toModel(data : ImageResponse) =
            ImageModel(
                nameImage = data.nameImage?: EMPTY_VALE
            )

    }
}