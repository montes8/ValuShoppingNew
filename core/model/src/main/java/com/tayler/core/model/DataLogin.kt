package com.tayler.core.model

import kotlinx.serialization.Serializable

@Serializable
data class DataLogin(
    val userValid: UserModel? = null,
    val token: String
)
