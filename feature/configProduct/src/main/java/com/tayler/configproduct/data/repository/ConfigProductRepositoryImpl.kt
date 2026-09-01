package com.tayler.configproduct.data.repository

import com.tayler.core.model.ImageModel
import com.tayler.core.model.ImageMoreModel
import com.tayler.core.model.ParamModel
import com.tayler.core.model.ProductImageModel
import com.tayler.core.model.ProductModel
import com.tayler.core.model.exception.GenericException
import com.tayler.core.network.base.BaseNetwork
import com.tayler.core.network.utils.processResponse
import com.tayler.configproduct.data.api.ConfigProductApiService
import com.tayler.configproduct.data.api.model.ImageMoreResponse
import com.tayler.configproduct.data.api.model.ImageResponse
import com.tayler.configproduct.data.api.model.ParamResponse
import com.tayler.configproduct.data.api.model.ProductImageRequest
import com.tayler.configproduct.data.api.model.ProductResponse
import com.tayler.configproduct.domain.repository.ConfigProductRepository
import com.tayler.core.common.utils.EMPTY_VALE
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ConfigProductRepositoryImpl @Inject constructor(
    private val api: ConfigProductApiService,
    private val base: BaseNetwork
) : ConfigProductRepository {

    override suspend fun saveProduct(data: ProductModel) = base.safeApiCall {
        api.saveProduct(ProductResponse.toModelRequest(data)).processResponse {
            ProductResponse.toModel(it)
        }
    }

    override suspend fun deleteProduct(idProduct: String) = base.safeApiCall {
        api.deleteProduct(idProduct).processResponse { ProductResponse.toModel(it) }
    }

    override suspend fun updateProduct(product: ProductModel) = base.safeApiCall {
        api.updateProduct(ProductResponse.toModelRequest(product)).processResponse {
            ProductResponse.toModel(it)
        }
    }

    override suspend fun saveImage(file: File?, nameFile: String) = base.safeApiCall {
        file?.let {
            val image = it.asRequestBody("image/*".toMediaType())
            val multiPartBody = MultipartBody.Part.createFormData("archivo", it.name, image)
            api.saveImage(multiPartBody, nameFile).processResponse { response ->
                ImageResponse.toModel(response)
            }
        } ?: throw GenericException()
    }

    override suspend fun saveImageMore(file: File?, nameFile: String) = base.safeApiCall {
        file?.let {
            val image = it.asRequestBody("image/*".toMediaType())
            val multiPartBody = MultipartBody.Part.createFormData("archivo", it.name, image)
            api.saveImageMore(multiPartBody, nameFile).processResponse { response ->
                ImageResponse.toModel(response)
            }
        } ?: throw GenericException()
    }

    override suspend fun loadProductImage(idProduct: String) = base.safeApiCall {
        api.loadProductImage(idProduct).processResponse { ImageMoreResponse.toList(it) }
    }

    override suspend fun deleteProductImage(idProduct: String) = base.safeApiCall {
        api.deleteProductImage(idProduct).processResponse { ImageMoreResponse.toModel(it) }
    }

    override suspend fun saveProductDBImages(request: ProductImageModel) = base.safeApiCall {
        api.saveProductImages(ProductImageRequest.toModel(request)).processResponse {
            ImageMoreResponse.toModel(it)
        }
    }

    override suspend fun loadParam(id: String) = base.safeApiCall {
        api.loadParam(id).processResponse { ParamResponse.toModel(it) }
    }

    override suspend fun saveParam(param: ParamModel) = base.safeApiCall {
        api.saveParam(ParamResponse.toModelRequest(param)).processResponse {
            ParamResponse.toModel(it)
        }
    }

    override suspend fun updateParam(param: ParamModel) = base.safeApiCall {
        api.updateParam(param.uid ?: EMPTY_VALE, ParamResponse.toModelRequest(param))
            .processResponse { ParamResponse.toModel(it) }
    }
}
