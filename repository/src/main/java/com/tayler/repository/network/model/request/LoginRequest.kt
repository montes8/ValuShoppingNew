package com.tayler.repository.network.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("nameUser")
    val nameUser: String,
    @SerializedName("password")
    val password: String
)