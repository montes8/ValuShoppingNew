package com.tayler.repository.network.model.response

import com.google.gson.annotations.SerializedName
import com.tayler.repository.utils.COUNTRY_DEFAULT
import com.tayler.repository.utils.DEFAULT_COUNT_PRODUCT
import com.tayler.repository.utils.DEFAULT_NUMBER
import com.tayler.repository.utils.DEFAULT_TEXT_WELCOME
import com.tayler.repository.utils.EMPTY_VALE
import com.tayler.repository.utils.HOUR_END_DEFAULT
import com.tayler.repository.utils.HOUR_START_DEFAULT
import com.tayler.repository.utils.ID_MOVIE_DEFAULT
import com.tayler.repository.utils.uiTayFormatTwelveHour

data class ParamResponse(
    @SerializedName("uid")
    var uid: String? = EMPTY_VALE,
    @SerializedName("title")
    var title: String? = DEFAULT_TEXT_WELCOME,
    @SerializedName("description")
    var description: String? = EMPTY_VALE,
    @SerializedName("idMovie")
    var idMovie: String? = ID_MOVIE_DEFAULT,
    @SerializedName("enableCategory")
    var enableCategory: Boolean? = false,
    @SerializedName("phone")
    var phone: String? = DEFAULT_NUMBER,
    @SerializedName("textWelcome")
    var textWelcome: String? = DEFAULT_TEXT_WELCOME,
    @SerializedName("hourStart")
    var hourStart: String? = HOUR_START_DEFAULT,
    @SerializedName("hourEnd")
    var hourEnd: String? = HOUR_END_DEFAULT,
    @SerializedName("limitDistance")
    var limitDistance: String? = "5",
    @SerializedName("countProduct")
    var countProduct: String? = DEFAULT_COUNT_PRODUCT,
    @SerializedName("styleValu")
    var styleValu: String? = "0",
    @SerializedName("bgService")
    var bgService: Boolean? = false,
    @SerializedName("bgToolbar")
    var bgToolbar: Boolean? = false,
    @SerializedName("countryCode")
    var countryCode: String? = COUNTRY_DEFAULT,
    @SerializedName("blocking")
    var blocking: Boolean? = null,
    @SerializedName("idIcon")
    var idIcon: String? = "Icon",
    @SerializedName("idFacebook")
    var idFacebook: String? = "61590557890653",
    @SerializedName("idYoutube")
    var idYoutube: String? = "xH6qsMpA7NM"
) {
    fun mapperDialogText(): String {
        return "Nuestros horario de atencion es de " +
                "${hourStart?.uiTayFormatTwelveHour()} a " +
                "${hourEnd?.uiTayFormatTwelveHour()}, gracias por su comprensión."
    }
}