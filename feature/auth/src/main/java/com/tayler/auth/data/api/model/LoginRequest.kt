package com.tayler.auth.data.api.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("nameUser")
    val nameUser: String,
    @SerializedName("password")
    val password: String
)