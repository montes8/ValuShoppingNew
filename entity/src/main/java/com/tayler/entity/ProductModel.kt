package com.tayler.entity

import kotlinx.serialization.Serializable

@Serializable
data class ProductModel(
    val uid: String = "",
    val name: String = "",
    val description: String = "",
    val type: String = "0",
    val category: String = "0",
    val price: String = "",
    val priceTwo: String = "",
    val state: Boolean = false,
    val img: String = "",
    val url: String = "",
    val urlBanner: String = "",
    val gender: String = "0",
    val phone: String = "",
    val principal: Boolean = false,
    val admin: Boolean = false,
    val idUser: String = "",
    val sizeHeight: String = "",
    val countryCode: String = "",
    val deliveryPoint: String = "",
    val district: String = "",
    val nameFile: String = "",
    val latitude: String = "0",
    val longitude: String = "0",
    val limitDistance: String = "0",
    val banner: Boolean = false,
    val linkBanner: String = "",
    val click: Boolean = true,
    val stateNew: Boolean = true,
    val sellerClient: String = ""
) {

    fun getPriceUnit() = "${getSymbolPrice()} $price"

    private fun getSymbolPrice(): String = if (countryCode == "AR") "$" else "S/"

    fun getPriceDoc() = "${getSymbolPrice()} $priceTwo"

    fun getPriceUnitTwo() = "c/u: ${getPriceUnit()}"

    fun getSeller() = "Vendedor: $sellerClient"

    fun getPriceDocTwo(): String {
        return if (visiblePriceDoc()) {
            "doc/: ${getPriceDoc()}"
        } else {
            "doc/: No disponible"
        }
    }

    fun visiblePriceDoc() = priceTwo.isNotEmpty() && priceTwo != "0.00"

    fun visiblePriceDocView() = priceTwo.isEmpty() || priceTwo == "0.00"

    fun visibleDelivery() = sizeHeight == "M" || sizeHeight == "B"

    fun getLinesProduct(): Int {
        return if (sizeHeight == "N") 3 else 4
    }

    fun getMapperTypeAndGender(data : List<CategoryModel>): String {
        return "${getMapperType(data)}  ${getMapperGender()}"
    }

    private fun getMapperType(data : List<CategoryModel>): String {
        val category = data.find { it.identifier == type }
        return category?.name ?: ""
    }

    private fun getMapperGender(): String {
        return when (gender) {
            "0" -> "Mujer"
            "1" -> "Varon"
            "2" -> "Unisex"
            else -> "Mujer"
        }
    }

    fun tyFlowCreate() = uid.isEmpty()
}
