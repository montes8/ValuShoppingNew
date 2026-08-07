package com.tayler.entity

import kotlinx.serialization.Serializable

@Serializable
data class ParamModel (
    val uid: String? = "",
    val title: String = "",
    val description: String = "",
    val idMovie: String = "",
    val enableCategory: Boolean = false,
    val phone: String = "935096444",
    val textWelcome: String = "",
    val hourStart: String? = "",
    val hourEnd: String? = "",
    val limitDistance: String? = "5",
    val countProduct: String? = "100",
    val styleValu: String = "0",
    val bgService: Boolean = false,
    val bgToolbar: Boolean? = false,
    val countryCode: String? = "PE",
    val blocking: Boolean? = null,
    val idIcon: String = "Principal",
    val idFacebook: String = "61590557890653",
    val idYoutube: String? = "xH6qsMpA7NM",
    val session: Boolean = false,
    val urlImage: String? = "",
    val idIconOld: String = "0",
)
