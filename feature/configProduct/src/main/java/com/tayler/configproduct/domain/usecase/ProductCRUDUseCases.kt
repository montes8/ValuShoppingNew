package com.tayler.configproduct.domain.usecase

import com.tayler.core.model.ProductModel
import com.tayler.configproduct.domain.repository.ConfigProductRepository
import javax.inject.Inject

class SaveProductUseCase @Inject constructor(private val repository: ConfigProductRepository) {
    suspend operator fun invoke(product: ProductModel) = repository.saveProduct(product)
}

class UpdateProductUseCase @Inject constructor(private val repository: ConfigProductRepository) {
    suspend operator fun invoke(product: ProductModel) = repository.updateProduct(product)
}

class DeleteProductUseCase @Inject constructor(private val repository: ConfigProductRepository) {
    suspend operator fun invoke(id: String) = repository.deleteProduct(id)
}
