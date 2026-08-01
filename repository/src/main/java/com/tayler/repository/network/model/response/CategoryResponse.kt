package com.tayler.repository.network.model.response

import com.google.gson.annotations.SerializedName
import com.tayler.repository.utils.EMPTY_VALE

data class CategoryResponse(
    @SerializedName("uid")
    var uid: String? = EMPTY_VALE,
    @SerializedName("name")
    var name: String? = EMPTY_VALE,
    @SerializedName("url")
    var url: String? = EMPTY_VALE,
    @SerializedName("identifier")
    var identifier: String? = EMPTY_VALE,
    @SerializedName("selected")
    var selected: Boolean? = false
)