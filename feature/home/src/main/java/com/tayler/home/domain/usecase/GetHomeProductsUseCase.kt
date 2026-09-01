package com.tayler.home.domain.usecase

import com.tayler.core.model.ProductModel
import com.tayler.home.domain.repository.HomeRepository
import javax.inject.Inject

class GetHomeProductsUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(all: Boolean, isUser: String, country: String): List<ProductModel> {
        return repository.getProducts(all, isUser, country)
    }
}
