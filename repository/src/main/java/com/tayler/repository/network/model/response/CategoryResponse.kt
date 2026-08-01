package com.tayler.repository.network.model.response

import com.google.gson.annotations.SerializedName
import com.tayler.entity.CategoryModel
import com.tayler.entity.UserBlockingModel
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