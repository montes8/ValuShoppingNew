package com.tayler.configproduct.domain.repository

import com.tayler.core.model.ImageModel
import com.tayler.core.model.ImageMoreModel
import com.tayler.core.model.ParamModel
import com.tayler.core.model.ProductImageModel
import com.tayler.core.model.ProductModel
import java.io.File

interface ConfigProductRepository {
    suspend fun saveProduct(data: ProductModel): ProductModel
    suspend fun deleteProduct(idProduct: String): ProductModel
    suspend fun updateProduct(product: ProductModel): ProductModel
    suspend fun saveImage(file: File?, nameFile: String): ImageModel
    suspend fun saveImageMore(file: File?, nameFile: String): ImageModel
    suspend fun loadProductImage(idProduct: String): List<ImageMoreModel>
    suspend fun deleteProductImage(idProduct: String): ImageMoreModel
    suspend fun saveProductDBImages(request: ProductImageModel): ImageMoreModel
    
    suspend fun loadParam(id: String): ParamModel
    suspend fun saveParam(param: ParamModel): ParamModel
    suspend fun updateParam(param: ParamModel): ParamModel
}
