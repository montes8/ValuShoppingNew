package com.tayler.repository.network.model.response

import com.google.gson.annotations.SerializedName
import com.tayler.entity.UserBlockingModel
import com.tayler.repository.utils.EMPTY_VALE

data class UserBlockingResponse(
    @SerializedName("uid")
    var uid: String? = EMPTY_VALE,
    @SerializedName("imei")
    var imei: String? = EMPTY_VALE,
    @SerializedName("identifierId")
    var identifierId: String? = EMPTY_VALE,
    @SerializedName("phone")
    var phone: String? = EMPTY_VALE,
    @SerializedName("description")
    var description: String? = EMPTY_VALE,
    @SerializedName("name")
    var name: String? = EMPTY_VALE,
    @SerializedName("ipAddress")
    var ipAddress: String? = EMPTY_VALE
){
    companion object{
        fun toList(data : List<UserBlockingResponse>) = data.map {item ->
            UserBlockingModel(
                uid = item.uid?: EMPTY_VALE,
                imei = item.imei?: EMPTY_VALE,
                identifierId = item.identifierId?: EMPTY_VALE,
                phone = item.phone?: EMPTY_VALE,
                description = item.description?: EMPTY_VALE,
                name = item.name ?: EMPTY_VALE,
                ipAddress = item.ipAddress ?: EMPTY_VALE,
            )
        }
    }
}