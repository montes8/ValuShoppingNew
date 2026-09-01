package com.tayler.home.data.api.model

import com.google.gson.annotations.SerializedName
import com.tayler.core.model.CategoryModel
import com.tayler.core.model.UserBlockingModel
import com.tayler.core.common.utils.EMPTY_VALE

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
){
    companion object{
        fun toList(data : List<CategoryResponse>) = data.map {item ->
            CategoryModel(
                uid = item.uid?: EMPTY_VALE,
                name = item.name?: EMPTY_VALE,
                url = item.url?: EMPTY_VALE,
                identifier = item.identifier?: EMPTY_VALE,
                selected = item.selected?: false
            )
        }
    }
}