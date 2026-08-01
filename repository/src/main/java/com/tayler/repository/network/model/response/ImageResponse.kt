package com.tayler.repository.network.model.response

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("nombre")
    var nameImage: String?
)