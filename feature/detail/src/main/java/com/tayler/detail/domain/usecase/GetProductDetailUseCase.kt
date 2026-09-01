package com.tayler.detail.domain.usecase

import com.tayler.core.model.ImageMoreModel
import com.tayler.detail.domain.repository.DetailRepository
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repository: DetailRepository
) {
    suspend operator fun invoke(idProduct: String): List<ImageMoreModel> {
        return repository.getProductImage(idProduct)
    }
}
