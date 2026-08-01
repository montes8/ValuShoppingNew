package com.tayler.repository.network.model.request

import com.google.gson.annotations.SerializedName

data class HistoryRequest(
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("imei")
    val imei: String,
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("hour")
    val hour: String,
    @SerializedName("ipAddress")
    val ipAddress: String,
    @SerializedName("numberPhone")
    val numberPhone: String
)