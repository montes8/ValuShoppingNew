package com.tayler.valushopping.ui.home.product

import com.tayler.entity.ImageMoreModel
import com.tayler.entity.ProductImageModel
import com.tayler.entity.ProductModel
import com.tayler.usecases.DataUseCase
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.ui.base.BaseViewModel
import com.tayler.valushopping.utils.TY_DEFAULT
import com.tayler.valushopping.utils.distance
import com.valu.uitaycompose.utils.UI_EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import java.io.File

@HiltViewModel
class DataViewModel @Inject constructor(
    private val dataUseCase: DataUseCase
) : BaseViewModel() {

    private val _successProductState = MutableStateFlow<ProductModel?>(null)
    val successProductState: StateFlow<ProductModel?> = _successProductState.asStateFlow()

    private val _successLoadProductState = MutableStateFlow<List<ProductModel>>(emptyList())
    val successLoadProductState: StateFlow<List<ProductModel>> = _successLoadProductState.asStateFlow()

    private val _successDeleteState = MutableStateFlow<ProductModel?>(null)
    val successDeleteState: StateFlow<ProductModel?> = _successDeleteState.asStateFlow()

    private val _successProductImageState = MutableStateFlow<List<ImageMoreModel>>(emptyList())
    val successProductImageState: StateFlow<List<ImageMoreModel>> = _successProductImageState.asStateFlow()

    private val _successMoreImageState = MutableStateFlow<ImageMoreModel?>(null)
    val successMoreImageState: StateFlow<ImageMoreModel?> = _successMoreImageState.asStateFlow()

    private val _successLoadProductCState = MutableStateFlow<List<ProductModel>>(emptyList())
    val successLoadProductCState: StateFlow<List<ProductModel>> = _successLoadProductCState.asStateFlow()

    private val _successLoadProductClientState = MutableStateFlow(
        Pair(
            List(5) { ProductModel(name = UI_EMPTY, url = UI_EMPTY) },
            List(1) { ProductModel(name = UI_EMPTY, urlBanner = UI_EMPTY) }
        )
    )
    val successLoadProductClientState: StateFlow<Pair<List<ProductModel>, List<ProductModel>>> = _successLoadProductClientState.asStateFlow()
    fun saveProduct(data: ProductModel) {
        execute {
            val nameFile = "${AppDataVale.user.nameUser}Imagenes"
            val responseImage = dataUseCase.saveImage(File(data.img), nameFile)
            data.img = responseImage.nameImage
            data.admin = AppDataVale.user.rol == "ADMIN"
            data.idUser = AppDataVale.user.uid
            data.url = "${AppDataVale.urlImage}uploads/product/$nameFile/${responseImage.nameImage}"
            data.nameFile = nameFile
            data.latitude = AppDataVale.user.latitude
            data.longitude = AppDataVale.user.longitude
            data.limitDistance = AppDataVale.user.limitDistance
            data.sellerClient = AppDataVale.user.sellerClient
            data.click = true
            val response = dataUseCase.saveProduct(data)
            _successProductState.value = response
        }
    }

    fun loadProduct(all: Boolean = false, admin: String = UI_EMPTY, country: String) {
        execute(false) {
            val response = dataUseCase.loadProduct(all, admin, country)
            _successLoadProductState.value = response.shuffled()
        }
    }

    fun loadProductCategory(category: String, country: String) {
        execute(false) {
            val response = dataUseCase.loadProductCategory(category).filter {
                it.countryCode.equals(country, ignoreCase = true)
            }
            _successLoadProductCState.value = response.shuffled()
        }
    }

    fun loadProductClient(all: Boolean = false, admin: String = UI_EMPTY, location: Boolean = false, country: String) {
        val currentProducts = _successLoadProductClientState.value.first
        val isAlreadyLoaded = currentProducts.firstOrNull()?.uid?.isNotEmpty() == true
        if (isAlreadyLoaded) return

        execute(false) {
            val listFilter: ArrayList<ProductModel> = ArrayList()
            val response = dataUseCase.loadProduct(all, admin, country)
            val listBanner = response.filter { it.banner == true }
            response.forEach {
                val distanceM = it.distance("K")
                if (distanceM < (getRangeFilterProduct(it)) || it.latitude == "0") {
                    listFilter.add(it)
                }
            }
            _successLoadProductClientState.value = Pair(if (location) listFilter.shuffled() else response.shuffled(), listBanner)
        }
    }

    private fun getRangeFilterProduct(it: ProductModel): Int {
        return if (it.limitDistance?.isEmpty() == true || it.limitDistance == TY_DEFAULT)
            AppDataVale.paramData.limitDistance?.toInt() ?: 5
        else it.limitDistance?.toInt() ?: 5
    }

    fun updateProduct(data: ProductModel) {
        execute {
            data.latitude = AppDataVale.user.latitude
            data.longitude = AppDataVale.user.longitude
            data.click = true
            val response = dataUseCase.updateProduct(data)
            _successProductState.value = response
        }
    }

    fun loadDeleteProduct(id: String) {
        execute {
            val response = dataUseCase.deleteProduct(id)
            _successDeleteState.value = response
        }
    }

    fun loadMoreImageProduct(id: String) {
        execute(false) {
            val response = dataUseCase.loadProductImage(id)
            _successProductImageState.value = response
        }
    }

    fun loadDeleteMoreProductImage(id: String) {
        execute {
            val response = dataUseCase.deleteProductImage(id)
            _successMoreImageState.value = response
        }
    }

    fun loadSaveMoreProductImage(data: ProductModel, file: String) {
        execute {
            val responseImage = dataUseCase.saveImageMore(File(file), data.phone ?: UI_EMPTY)
            val request = ProductImageModel(
                responseImage.nameImage,
                data.uid,
                data.idUser,
                "${AppDataVale.urlImage}uploads/productMore/${data.phone}/${responseImage.nameImage}",
                data.phone
            )
            val response = dataUseCase.saveProductDBImages(request)
            _successMoreImageState.value = response
        }
    }
}