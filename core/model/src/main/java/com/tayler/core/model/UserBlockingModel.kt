package com.tayler.core.model

import kotlinx.serialization.Serializable

@Serializable
data class UserBlockingModel (
    val uid: String = "",
    val imei: String = "",
    val identifierId: String = "",
    val phone: String? = "",
    val description: String = "",
    val name: String? = "",
    val ipAddress: String = ""
)
