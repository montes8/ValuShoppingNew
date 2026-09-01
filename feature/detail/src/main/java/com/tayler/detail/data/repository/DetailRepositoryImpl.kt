package com.tayler.detail.data.repository

import com.tayler.core.model.ImageMoreModel
import com.tayler.core.network.base.BaseNetwork
import com.tayler.core.network.utils.processResponse
import com.tayler.detail.data.api.DetailApiService
import com.tayler.detail.data.api.model.ImageMoreResponse
import com.tayler.detail.domain.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val api: DetailApiService,
    private val base: BaseNetwork
) : DetailRepository {
    override suspend fun getProductImage(idProduct: String): List<ImageMoreModel> = base.safeApiCall {
        api.loadProductImage(idProduct).processResponse { ImageMoreResponse.toList(it) }
    }
}
