package com.tayler.home.domain.repository

import com.tayler.core.model.CategoryModel
import com.tayler.core.model.ProductModel

interface HomeRepository {
    suspend fun getProducts(all: Boolean, isUser: String, country: String): List<ProductModel>
    suspend fun getCategories(): List<CategoryModel>
    suspend fun getCategoriesAll(): List<CategoryModel>
}
