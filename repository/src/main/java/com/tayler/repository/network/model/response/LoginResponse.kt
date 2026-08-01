package com.tayler.repository.network.model.response

import com.google.gson.annotations.SerializedName
import com.tayler.entity.DataLogin
import com.tayler.repository.utils.EMPTY_VALE

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