package com.tayler.repository.network.protocol

import com.tayler.entity.ImageModel
import com.tayler.entity.ImageMoreModel
import com.tayler.entity.ProductImageModel
import com.tayler.entity.ProductModel
import java.io.File

interface IDataNetwork {
    suspend fun saveProduct(data: ProductModel): ProductModel
    suspend fun loadProduct(all: Boolean, isUser: String,country : String): List<ProductModel>
    suspend fun deleteProduct(idProduct: String): ProductModel
    suspend fun updateProduct(product: ProductModel): ProductModel
    suspend fun saveImage(file: File?, nameFile: String): ImageModel
    suspend fun loadProductImage(idProduct: String): List<ImageMoreModel>
    suspend fun deleteProductImage(idProduct: String): ImageMoreModel
    suspend fun saveImageMore(file: File?, nameFile: String): ImageModel
    suspend fun saveProductDBImages(request: ProductImageModel): ImageMoreModel
    suspend fun loadProductCategory(category : String): List<ProductModel>
}