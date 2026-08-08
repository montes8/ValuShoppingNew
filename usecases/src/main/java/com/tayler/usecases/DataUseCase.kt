package com.tayler.usecases

import com.tayler.entity.ImageModel
import com.tayler.entity.ImageMoreModel
import com.tayler.entity.ProductImageModel
import com.tayler.entity.ProductModel
import com.tayler.repository.network.protocol.IDataNetwork
import jakarta.inject.Inject
import java.io.File

class DataUseCase @Inject constructor(
    private val dataNetwork: IDataNetwork
) {

    suspend fun saveProduct(data: ProductModel): ProductModel {
        return dataNetwork.saveProduct(data)
    }

    suspend fun loadProduct(all: Boolean, isUser: String, country: String): List<ProductModel> {
        return dataNetwork.loadProduct(all, isUser, country)
    }

    suspend fun deleteProduct(idProduct: String): ProductModel {
        return dataNetwork.deleteProduct(idProduct)
    }

    suspend fun updateProduct(product: ProductModel): ProductModel {
        return dataNetwork.updateProduct(product)
    }

    suspend fun saveImage(file: File?, nameFile: String): ImageModel {
        return dataNetwork.saveImage(file, nameFile)
    }

    suspend fun loadProductImage(idProduct: String): List<ImageMoreModel> {
        return dataNetwork.loadProductImage(idProduct)
    }

    suspend fun deleteProductImage(idProduct: String): ImageMoreModel {
        return dataNetwork.deleteProductImage(idProduct)
    }

    suspend fun saveImageMore(file: File?, nameFile: String): ImageModel {
        return dataNetwork.saveImageMore(file, nameFile)
    }

    suspend fun saveProductDBImages(request: ProductImageModel): ImageMoreModel {
        return dataNetwork.saveProductDBImages(request)
    }

    suspend fun loadProductCategory(category: String): List<ProductModel> {
        return dataNetwork.loadProductCategory(category)
    }
}
