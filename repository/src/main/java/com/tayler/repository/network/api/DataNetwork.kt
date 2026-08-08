package com.tayler.repository.network.api

import com.tayler.entity.ProductImageModel
import com.tayler.entity.ProductModel
import com.tayler.entity.exception.GenericException
import com.tayler.repository.network.ServiceApi
import com.tayler.repository.network.base.BaseNetwork
import com.tayler.repository.network.model.request.ProductImageRequest
import com.tayler.repository.network.model.response.ImageMoreResponse
import com.tayler.repository.network.model.response.ImageResponse
import com.tayler.repository.network.model.response.ProductResponse
import com.tayler.repository.network.protocol.IDataNetwork
import com.tayler.repository.utils.processResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class DataNetwork @Inject constructor(
    private val serviceApi: ServiceApi,
    private val base: BaseNetwork
) : IDataNetwork {

    override suspend fun saveProduct(data: ProductModel) = base.safeApiCall {
        serviceApi.saveProduct(ProductResponse.toModelRequest(data)).processResponse {
            ProductResponse.toModel(it)
        }
    }

    override suspend fun loadProduct(all: Boolean, isUser: String, country: String) = base.safeApiCall {
        val response = if (all) serviceApi.loadProducts(isUser) else serviceApi.loadProduct(country)
        response.processResponse { ProductResponse.toList(it) }
    }

    override suspend fun loadProductCategory(category: String) = base.safeApiCall {
        serviceApi.loadProductCategory(category).processResponse { ProductResponse.toList(it) }
    }

    override suspend fun deleteProduct(idProduct: String) = base.safeApiCall {
        serviceApi.deleteProduct(idProduct).processResponse { ProductResponse.toModel(it) }
    }

    override suspend fun deleteProductImage(idProduct: String) = base.safeApiCall {
        serviceApi.deleteProductImage(idProduct).processResponse { ImageMoreResponse.toModel(it) }
    }

    override suspend fun updateProduct(product: ProductModel) = base.safeApiCall {
        serviceApi.updateProduct(ProductResponse.toModelRequest(product)).processResponse {
            ProductResponse.toModel(it)
        }
    }

    override suspend fun saveImage(file: File?, nameFile: String) = base.safeApiCall {
        file?.let {
            val image = it.asRequestBody("image/*".toMediaType())
            val multiPartBody = MultipartBody.Part.createFormData("archivo", it.name, image)
            serviceApi.saveImage(multiPartBody, nameFile).processResponse { response ->
                ImageResponse.toModel(response)
            }
        } ?: throw GenericException()
    }

    override suspend fun saveImageMore(file: File?, nameFile: String) = base.safeApiCall {
        file?.let {
            val image = it.asRequestBody("image/*".toMediaType())
            val multiPartBody = MultipartBody.Part.createFormData("archivo", it.name, image)
            serviceApi.saveImageMore(multiPartBody, nameFile).processResponse { response ->
                ImageResponse.toModel(response)
            }
        } ?: throw GenericException()
    }

    override suspend fun saveProductDBImages(request: ProductImageModel) = base.safeApiCall {
        serviceApi.saveProductImages(ProductImageRequest.toModel(request)).processResponse {
            ImageMoreResponse.toModel(it)
        }
    }

    override suspend fun loadProductImage(idProduct: String) = base.safeApiCall {
        serviceApi.loadProductImage(idProduct).processResponse { ImageMoreResponse.toList(it) }
    }
}
