package com.tayler.home.domain.usecase

import com.tayler.core.model.CategoryModel
import com.tayler.home.domain.repository.HomeRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend fun getCategories(): List<CategoryModel> {
        return repository.getCategories()
    }

    suspend fun getCategoriesAll(): List<CategoryModel> {
        return repository.getCategoriesAll()
    }
}
