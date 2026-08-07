package com.tayler.repository.network.exception

import com.google.gson.annotations.SerializedName
import com.tayler.entity.exception.UiTayApiException
import com.tayler.repository.utils.DEFAULT_CODE
import com.tayler.repository.utils.ERROR_MESSAGE_GENERAL
import com.tayler.repository.utils.ERROR_TITLE_GENERAL


data class CompleteErrorModel(
    @SerializedName("errorCode")
    var code: Int? = DEFAULT_CODE,
    @SerializedName("title")
    val title: String? = ERROR_TITLE_GENERAL,
    @SerializedName("description")
    val description: String? = ERROR_MESSAGE_GENERAL
) : Exception(description) {

    fun getApiException(): Exception {
        return UiTayApiException(
            this.code ?: 0,
            this.title ?: ERROR_MESSAGE_GENERAL,
            this.description ?: ERROR_MESSAGE_GENERAL
        )
    }
}