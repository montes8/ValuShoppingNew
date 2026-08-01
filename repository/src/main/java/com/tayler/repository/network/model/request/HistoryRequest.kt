package com.tayler.repository.network.model.request

import com.google.gson.annotations.SerializedName
import com.tayler.entity.HistoryModel

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
){
    companion object{
        fun toModel(data : HistoryModel) = HistoryRequest(
            type = data.type,
            name = data.name,
            latitude = data.latitude,
            longitude = data.longitude,
            address = data.address,
            imei = data.imei,
            identifier = data.identifier,
            date = data.date,
            hour = data.hour,
            ipAddress = data.ipAddress,
            numberPhone = data.numberPhone,

        )
    }
}