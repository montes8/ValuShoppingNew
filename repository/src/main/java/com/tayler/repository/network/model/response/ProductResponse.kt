package com.tayler.repository.network.model.response

import com.google.gson.annotations.SerializedName
import com.tayler.entity.CategoryModel
import com.tayler.entity.ProductModel
import com.tayler.entity.UserBlockingModel
import com.tayler.repository.utils.EMPTY_VALE
import kotlin.String

data class ProductResponse(
    @SerializedName("uid")
    var uid: String? = EMPTY_VALE,
    @SerializedName("name")
    var name: String? = EMPTY_VALE,
    @SerializedName("description")
    var description: String? = EMPTY_VALE,
    @SerializedName("type")
    var type: String? = "0",
    @SerializedName("category")
    var category: String? = "0",
    @SerializedName("price")
    var price: String? = EMPTY_VALE,
    @SerializedName("priceTwo")
    var priceTwo: String? = EMPTY_VALE,
    @SerializedName("state")
    var state: Boolean? = false,
    @SerializedName("img")
    var img: String? = EMPTY_VALE,
    @SerializedName("url")
    var url: String? = EMPTY_VALE,
    @SerializedName("urlBanner")
    var urlBanner: String? = EMPTY_VALE,
    @SerializedName("gender")
    var gender: String? = "0",
    @SerializedName("phone")
    var phone: String? = EMPTY_VALE,
    @SerializedName("principal")
    var principal: Boolean? = false,
    @SerializedName("admin")
    var admin: Boolean? = false,
    @SerializedName("idUser")
    var idUser: String? = EMPTY_VALE,
    @SerializedName("sizeHeight")
    var sizeHeight: String? = "N",
    @SerializedName("countryCode")
    var countryCode: String? = EMPTY_VALE,
    @SerializedName("deliveryPoint")
    var deliveryPoint: String? = EMPTY_VALE,
    @SerializedName("district")
    var district: String? = EMPTY_VALE,
    @SerializedName("nameFile")
    var nameFile: String? = EMPTY_VALE,
    @SerializedName("latitude")
    var latitude: String? = "0",
    @SerializedName("longitude")
    var longitude: String? = "0",
    @SerializedName("limitDistance")
    var limitDistance: String? = "5",
    @SerializedName("banner")
    var banner: Boolean? = false,
    @SerializedName("linkBanner")
    var linkBanner: String? = EMPTY_VALE,
    @SerializedName("click")
    var click: Boolean? = true,
    @SerializedName("stateNew")
    var stateNew: Boolean? = true,
    @SerializedName("sellerClient")
    var sellerClient: String? = EMPTY_VALE,
) {
    companion object{
        fun toList(data : List<ProductResponse>) = data.map {item ->
            toModel(item)
        }

        fun toModel(item : ProductResponse) =
            ProductModel(
                uid = item.uid ?: EMPTY_VALE,
                name = item.name ?: EMPTY_VALE,
                description = item.description ?: EMPTY_VALE,
                type = item.type ?:"0",
                category = item.category ?:"0",
                price = item.price ?:EMPTY_VALE,
                priceTwo = item.priceTwo ?:EMPTY_VALE,
                state = item.state ?:false,
                img = item.img ?:EMPTY_VALE,
                url = item.url ?:EMPTY_VALE,
                urlBanner = item.urlBanner ?:EMPTY_VALE,
                gender = item.gender ?:"0",
                phone = item.phone ?:EMPTY_VALE,
                principal = item.principal ?:false,
                admin = item.admin ?:false,
                idUser = item.idUser ?:EMPTY_VALE,
                sizeHeight = item.sizeHeight ?:"N",
                countryCode = item.countryCode ?:EMPTY_VALE,
                deliveryPoint = item.deliveryPoint ?:EMPTY_VALE,
                district = item.district ?:EMPTY_VALE,
                nameFile = item.nameFile ?:EMPTY_VALE,
                latitude = item.latitude ?:"0",
                longitude = item.longitude ?:"0",
                limitDistance = item.limitDistance ?:"5",
                banner = item.banner ?:false,
                linkBanner = item.linkBanner ?:EMPTY_VALE,
                click = item.click ?:true,
                stateNew = item.stateNew ?:true,
                sellerClient = item.sellerClient ?:EMPTY_VALE)


        fun toModelRequest(item: ProductModel) =
            ProductResponse(
                uid = item.uid,
                name = item.name,
                description = item.description,
                type = item.type,
                category = item.category,
                price = item.price,
                priceTwo = item.priceTwo,
                state = item.state,
                img = item.img,
                url = item.url,
                urlBanner = item.urlBanner,
                gender = item.gender,
                phone = item.phone,
                principal = item.principal,
                admin = item.admin,
                idUser = item.idUser,
                sizeHeight = item.sizeHeight,
                countryCode = item.countryCode,
                deliveryPoint = item.deliveryPoint,
                district = item.district,
                nameFile = item.nameFile,
                latitude = item.latitude,
                longitude = item.longitude,
                limitDistance = item.limitDistance,
                banner = item.banner,
                linkBanner = item.linkBanner,
                click = item.click,
                stateNew = item.stateNew,
                sellerClient = item.sellerClient
            )
    }
}