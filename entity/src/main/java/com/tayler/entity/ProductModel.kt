package com.tayler.entity

class ProductModel(
    var uid: String? = "",
    var name: String? = "",
    var description: String? = "",
    var type: String? = "0",
    var category: String? = "0",
    var price: String? = "",
    var priceTwo: String? = "",
    var state: Boolean? = false,
    var img: String? = "",
    var url: String? = "",
    var urlBanner: String? = "",
    var gender: String? = "0",
    var phone: String? = "",
    var principal: Boolean? = false,
    var admin: Boolean? = false,
    var idUser: String? = "",
    var sizeHeight: String? = "",
    var countryCode: String? = "",
    var deliveryPoint: String? = "",
    var district: String? = "",
    var nameFile: String? = "",
    var latitude: String? = "0",
    var longitude: String? = "0",
    var limitDistance: String? = "0",
    var banner: Boolean? = false,
    var linkBanner: String? = "",
    var click: Boolean? = true,
    var stateNew: Boolean? = true,
    var sellerClient: String? = ""
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

    fun visiblePriceDoc() = priceTwo?.isNotEmpty() == true && priceTwo != "0.00"

    fun visiblePriceDocView() = priceTwo?.isEmpty() == true || priceTwo == "0.00"

    fun visibleDelivery() = sizeHeight == "M" || sizeHeight == "B"

    fun getLinesProduct(): Int {
        return if (sizeHeight == "N") 3 else 4
    }

    private fun getMapperGender(): String {
        return when (gender) {
            "0" -> "Mujer"
            "1" -> "Varon"
            "2" -> "Unisex"
            else -> "Mujer"
        }
    }

    fun tyFlowCreate() = uid?.isEmpty() == true
}