package com.tayler.repository.network.api

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
    override suspend fun saveProduct(data: ProductResponse): ProductResponse {
        return base.executeWithConnection {
            var model: ProductResponse? = null
            val response = serviceApi.saveProduct(data)
            if (response.validateData()) {
                model = response.validateBody()
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())

        }
    }

    override suspend fun loadProduct(all: Boolean, isUser: String,country : String): List<ProductResponse> {
        return base.executeWithConnection {
            var model: List<ProductResponse>? = null
            val response = if (all) serviceApi.loadProducts(isUser) else serviceApi.loadProduct(country)
            if (response.validateData()) {
                model = response.validateBody()
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }

    override suspend fun loadProductCategory(category : String): List<ProductResponse> {
        return base.executeWithConnection {
            var model: List<ProductResponse>? = null
            val response = serviceApi.loadProductCategory(category)
            if (response.validateData()) {
                model = response.validateBody()
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())

        }
    }

    override suspend fun deleteProduct(idProduct: String): ProductResponse {
        return base.executeWithConnection {
            var model: ProductResponse? = null
            val response = serviceApi.deleteProduct(idProduct)
            if (response.validateData()) {
                model = response.validateBody()
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }

    override suspend fun deleteProductImage(idProduct: String): ImageMoreResponse {
        return base.executeWithConnection {
            var model: ImageMoreResponse? = null
            val response = serviceApi.deleteProductImage(idProduct)
            if (response.validateData()) {
                model = response.validateBody()
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }

    override suspend fun updateProduct(product: ProductResponse): ProductResponse {
        return base.executeWithConnection {
            var model: ProductResponse? = null
            val response = serviceApi.updateProduct(product)
            if (response.validateData()) {
                model = response.validateBody()
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())

        }
    }

    override suspend fun saveImage(file: File?,nameFile: String): ImageResponse {
        return base.executeWithConnection {
            file?.let {
                val image = it.asRequestBody("image/*".toMediaType())
                val multiPartBody = MultipartBody.Part.createFormData("archivo", it.name, image)
                val response = serviceApi.saveImage(multiPartBody,nameFile)
                var model: ImageResponse? = null
                if (response.isSuccessful && response.body() != null) {
                    model = response.validateBody()
                }
                model ?: throw response.errorBody().toCompleteErrorModel(response.code())
            } ?: throw GenericException()

        }
    }

    override suspend fun saveImageMore(file: File?, nameFile: String): ImageResponse {
        return base.executeWithConnection {
            file?.let {
                val image = it.asRequestBody("image/*".toMediaType())
                val multiPartBody = MultipartBody.Part.createFormData("archivo", it.name, image)
                val response = serviceApi.saveImageMore(multiPartBody, nameFile)
                var model: ImageResponse? = null
                if (response.isSuccessful && response.body() != null) {
                    model = response.validateBody()
                }
                model ?: throw response.errorBody().toCompleteErrorModel(response.code())
            } ?: throw GenericException()

        }
    }

    override suspend fun saveProductDBImages(request: ProductImageRequest): ImageMoreResponse {
        return base.executeWithConnection {
            var model: ImageMoreResponse? = null
            val response = serviceApi.saveProductImages(request)
            if (response.validateData()) {
                model = response.validateBody()
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())

        }
    }

    override suspend fun loadProductImage(idProduct: String): List<ImageMoreResponse> {
        return base.executeWithConnection {
            var model: List<ImageMoreResponse>? = null
            val response = serviceApi.loadProductImage(idProduct)
            if (response.validateData()) {
                model = response.validateBody()
            }
            model ?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }
}