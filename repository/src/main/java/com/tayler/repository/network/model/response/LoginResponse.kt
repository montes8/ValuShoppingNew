package com.tayler.repository.network.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("userValid")
    val userValid: UserResponse? = null,
    @SerializedName("token")
    val token: String
)