package com.tayler.repository.network.model.response

import com.google.gson.annotations.SerializedName
import com.tayler.repository.utils.EMPTY_VALE

data class TaskResponse(
    @SerializedName("uid")
    var uid: String? = EMPTY_VALE,
    @SerializedName("course")
    var course: String? = EMPTY_VALE,
    @SerializedName("issue")
    var issue: String? = EMPTY_VALE,
    @SerializedName("concept")
    var concept: String? = EMPTY_VALE,
    @SerializedName("titleOne")
    var titleOne: String? = EMPTY_VALE,
    @SerializedName("conceptOne")
    var conceptOne: String? = EMPTY_VALE,
    @SerializedName("titleTwo")
    var titleTwo: String? = EMPTY_VALE,
    @SerializedName("conceptTwo")
    var conceptTwo: String? = EMPTY_VALE,
    @SerializedName("titleThree")
    var titleThree: String? = EMPTY_VALE,
    @SerializedName("conceptThree")
    var conceptThree: String? = EMPTY_VALE
)
