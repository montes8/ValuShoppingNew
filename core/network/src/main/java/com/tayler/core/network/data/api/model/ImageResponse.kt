package com.tayler.core.network.data.api.model

import com.google.gson.annotations.SerializedName
import com.tayler.core.model.ImageModel
import com.tayler.core.model.ImageMoreModel
import com.tayler.core.common.utils.EMPTY_VALE

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