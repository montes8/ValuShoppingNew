package com.tayler.repository.network.model.response

import com.google.gson.annotations.SerializedName
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
)