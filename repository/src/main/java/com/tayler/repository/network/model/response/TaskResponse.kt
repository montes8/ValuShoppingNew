package com.tayler.repository.network.model.response

import com.google.gson.annotations.SerializedName
import com.tayler.entity.TaskModel
import com.tayler.entity.UserBlockingModel
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
){
    companion object{
        fun toList(data : List<TaskResponse>) = data.map {item ->
            TaskModel(
                uid = item.uid ?: EMPTY_VALE,
                course = item.course ?: EMPTY_VALE,
                issue = item.issue ?: EMPTY_VALE,
                concept = item.concept ?: EMPTY_VALE,
                titleOne = item.titleOne ?: EMPTY_VALE,
                conceptOne = item.conceptOne ?: EMPTY_VALE,
                titleTwo = item.titleTwo ?: EMPTY_VALE,
                conceptTwo = item.conceptTwo ?: EMPTY_VALE,
                titleThree = item.titleThree ?: EMPTY_VALE,
                conceptThree = item.conceptThree ?: EMPTY_VALE
            )
        }
    }
}
