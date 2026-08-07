package com.tayler.entity

import kotlinx.serialization.Serializable

@Serializable
data class DataLogin(
    val userValid: UserModel? = null,
    val token: String
)
