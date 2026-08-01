package com.tayler.repository.network.api

import com.tayler.entity.ImageModel
import com.tayler.entity.ImageMoreModel
import com.tayler.entity.ProductImageModel
import com.tayler.entity.ProductModel
import com.tayler.repository.network.ServiceApi
import com.tayler.repository.network.base.BaseNetwork
import com.tayler.repository.network.exception.GenericException
import com.tayler.repository.network.model.request.ProductImageRequest
import com.tayler.repository.network.model.response.ImageMoreResponse
import com.tayler.repository.network.model.response.ImageResponse
import com.tayler.repository.network.model.response.ProductResponse
import com.tayler.repository.network.protocol.IDataNetwork
import com.tayler.repository.utils.toCompleteErrorModel
import com.tayler.repository.utils.validateBody
import com.tayler.repository.utils.validateData
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class DataNetwork @Inject constructor(
    private val serviceApi: ServiceApi,
    private val base: BaseNetwork
) :
    IDataNetwork {
    override suspend fun saveProduct(data: ProductModel): ProductModel {
        return base.executeWithConnection {
            var model: ProductModel? = null
            val response = serviceApi.saveProduct(ProductResponse.toModelRequest(data))
            if (response.validateData()) {
                model = ProductResponse.toModel(response.validateBody() )
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())

        }
    }

    override suspend fun loadProduct(all: Boolean, isUser: String,country : String): List<ProductModel> {
        return base.executeWithConnection {
            var model: List<ProductModel>? = null
            val response = if (all) serviceApi.loadProducts(isUser) else serviceApi.loadProduct(country)
            if (response.validateData()) {
                model = ProductResponse.toList(response.validateBody() )
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }

    override suspend fun loadProductCategory(category : String): List<ProductModel> {
        return base.executeWithConnection {
            var model: List<ProductModel>? = null
            val response = serviceApi.loadProductCategory(category)
            if (response.validateData()) {
                model =  ProductResponse.toList(response.validateBody() )
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())

        }
    }

    override suspend fun deleteProduct(idProduct: String): ProductModel {
        return base.executeWithConnection {
            var model: ProductModel? = null
            val response = serviceApi.deleteProduct(idProduct)
            if (response.validateData()) {
                model =  ProductResponse.toModel(response.validateBody() )
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }

    override suspend fun deleteProductImage(idProduct: String): ImageMoreModel {
        return base.executeWithConnection {
            var model: ImageMoreModel? = null
            val response = serviceApi.deleteProductImage(idProduct)
            if (response.validateData()) {
                model = ImageMoreResponse.toModel(response.validateBody())
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }

    override suspend fun updateProduct(product: ProductModel): ProductModel {
        return base.executeWithConnection {
            var model: ProductModel? = null
            val response = serviceApi.updateProduct(ProductResponse.toModelRequest(product))
            if (response.validateData()) {
                model =  ProductResponse.toModel(response.validateBody() )
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())

        }
    }

    override suspend fun saveImage(file: File?,nameFile: String): ImageModel {
        return base.executeWithConnection {
            file?.let {
                val image = it.asRequestBody("image/*".toMediaType())
                val multiPartBody = MultipartBody.Part.createFormData("archivo", it.name, image)
                val response = serviceApi.saveImage(multiPartBody,nameFile)
                var model: ImageModel? = null
                if (response.isSuccessful && response.body() != null) {
                    model = ImageResponse.toModel(response.validateBody())
                }
                model ?: throw response.errorBody().toCompleteErrorModel(response.code())
            } ?: throw GenericException()

        }
    }

    override suspend fun saveImageMore(file: File?, nameFile: String): ImageModel {
        return base.executeWithConnection {
            file?.let {
                val image = it.asRequestBody("image/*".toMediaType())
                val multiPartBody = MultipartBody.Part.createFormData("archivo", it.name, image)
                val response = serviceApi.saveImageMore(multiPartBody, nameFile)
                var model: ImageModel? = null
                if (response.isSuccessful && response.body() != null) {
                    model = ImageResponse.toModel(response.validateBody())
                }
                model ?: throw response.errorBody().toCompleteErrorModel(response.code())
            } ?: throw GenericException()

        }
    }

    override suspend fun saveProductDBImages(request: ProductImageModel): ImageMoreModel {
        return base.executeWithConnection {
            var model: ImageMoreModel? = null
            val response = serviceApi.saveProductImages(
                ProductImageRequest.toModel(request))
            if (response.validateData()) {
                model = ImageMoreResponse.toModel(response.validateBody())
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())

        }
    }

    override suspend fun loadProductImage(idProduct: String): List<ImageMoreModel> {
        return base.executeWithConnection {
            var model: List<ImageMoreModel>? = null
            val response = serviceApi.loadProductImage(idProduct)
            if (response.validateData()) {
                model = ImageMoreResponse.toList(response.validateBody())
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }
}