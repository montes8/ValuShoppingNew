package com.tayler.entity

import kotlinx.serialization.Serializable

@Serializable
data class TaskModel (
    val uid: String? = "",
    val course: String? =  "",
    val issue: String? =  "",
    val concept: String? =  "",
    val titleOne: String? =  "",
    val conceptOne: String? =  "",
    val titleTwo: String? =  "",
    val conceptTwo: String? =  "",
    val titleThree: String? =  "",
    val conceptThree: String? =  ""
)
