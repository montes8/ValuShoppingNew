package com.tayler.core.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoryModel (
    val uid: String = "",
    val name: String =  "",
    val url: String =  "",
    val identifier: String =  "",
    val selected: Boolean = false
)
