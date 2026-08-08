package com.tayler.usecases

import com.tayler.entity.ImageMoreModel
import com.tayler.entity.ProductModel
import com.tayler.repository.network.protocol.IDataNetwork
import jakarta.inject.Inject

class DataUseCase @Inject constructor(
    private val dataNetwork: IDataNetwork
) {

    suspend fun loadProduct(all: Boolean, isUser: String, country: String): List<ProductModel> {
        return dataNetwork.loadProduct(all, isUser, country)
    }

    suspend fun loadProductImage(idProduct: String): List<ImageMoreModel> {
        return dataNetwork.loadProductImage(idProduct)
    }
}
