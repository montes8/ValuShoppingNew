package com.tayler.detail.domain.repository

import com.tayler.core.model.ImageMoreModel

interface DetailRepository {
    suspend fun getProductImage(idProduct: String): List<ImageMoreModel>
}
