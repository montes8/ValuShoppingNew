package com.tayler.usecases

import com.tayler.entity.ImageModel
import com.tayler.entity.ImageMoreModel
import com.tayler.entity.ProductImageModel
import com.tayler.entity.ProductModel
import com.tayler.repository.network.protocol.IDataNetwork
import jakarta.inject.Inject
import java.io.File

class DataUseCase @Inject constructor(
    private val iDataNetwork: IDataNetwork
) {

    suspend fun saveProduct(data: ProductModel): ProductModel{
        return iDataNetwork.saveProduct(data)
    }
    suspend fun loadProduct(all: Boolean, isUser: String,country : String): List<ProductModel>{
        return iDataNetwork.loadProduct(all,isUser,country)
    }
    suspend fun deleteProduct(idProduct: String): ProductModel{
        return iDataNetwork.deleteProduct(idProduct)
    }
    suspend fun updateProduct(product: ProductModel): ProductModel{
        return iDataNetwork.updateProduct(product)
    }
    suspend fun saveImage(file: File?, nameFile: String): ImageModel{
        return iDataNetwork.saveImage(file,nameFile)
    }
    suspend fun loadProductImage(idProduct: String): List<ImageMoreModel>{
        return iDataNetwork.loadProductImage(idProduct)
    }
    suspend fun deleteProductImage(idProduct: String): ImageMoreModel{
        return iDataNetwork.deleteProductImage(idProduct)
    }
    suspend fun saveImageMore(file: File?, nameFile: String): ImageModel{
        return iDataNetwork.saveImageMore(file,nameFile)
    }
    suspend fun saveProductDBImages(request: ProductImageModel): ImageMoreModel{
        return iDataNetwork.saveProductDBImages(request)
    }
    suspend fun loadProductCategory(category : String): List<ProductModel>{
        return iDataNetwork.loadProductCategory(category)
    }
}