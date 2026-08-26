package com.tayler.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SecurityAlertRequest(
    @SerialName("event")
    val event: String,
    @SerialName("packageApp")
    val packageApp: String,
    @SerialName("version")
    val version: String,
    @SerialName("timestamp")
    val timestamp: String,
    @SerialName("model")
    val model: String,
    @SerialName("reason")
    val reason: String,
    @SerialName("uuid")
    val uuid: String,
    @SerialName("identifier")
    val identifier: String,
    @SerialName("installer")
    val installer: String
)
