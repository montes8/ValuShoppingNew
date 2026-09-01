package com.tayler.auth.data.api.model

import com.google.gson.annotations.SerializedName
import com.tayler.core.model.DataLogin
import com.tayler.core.common.utils.EMPTY_VALE

data class LoginResponse(
    @SerializedName("userValid")
    val userValid: UserResponse? = null,
    @SerializedName("token")
    val token: String? = EMPTY_VALE
){
    companion object{
        fun toModel(data :LoginResponse ) = DataLogin(
            userValid = data.userValid?.toModel(),
            token = data.token?:EMPTY_VALE,
        )
    }
}