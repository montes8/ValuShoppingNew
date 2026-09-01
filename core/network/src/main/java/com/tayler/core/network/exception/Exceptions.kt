package com.tayler.core.network.exception

import com.google.gson.annotations.SerializedName
import com.tayler.core.model.exception.UiTayApiException
import com.tayler.core.common.utils.DEFAULT_CODE
import com.tayler.core.common.utils.ERROR_MESSAGE_GENERAL
import com.tayler.core.common.utils.ERROR_TITLE_GENERAL


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