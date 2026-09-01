package com.tayler.home.data.repository

import com.tayler.core.model.CategoryModel
import com.tayler.core.model.ProductModel
import com.tayler.core.network.base.BaseNetwork
import com.tayler.core.network.utils.processResponse
import com.tayler.home.data.api.HomeApiService
import com.tayler.home.data.api.model.CategoryResponse
import com.tayler.home.data.api.model.ProductResponse
import com.tayler.home.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: HomeApiService,
    private val base: BaseNetwork
) : HomeRepository {

    override suspend fun getProducts(all: Boolean, isUser: String, country: String): List<ProductModel> = 
        base.safeApiCall {
            val response = if (all) api.loadProducts(isUser) else api.loadProduct(country)
            response.processResponse { ProductResponse.toList(it) }
        }

    override suspend fun getCategories(): List<CategoryModel> = 
        base.safeApiCall {
            api.loadCategories().processResponse { CategoryResponse.toList(it) }
        }

    override suspend fun getCategoriesAll(): List<CategoryModel> = 
        base.safeApiCall {
            api.loadCategoriesAll().processResponse { CategoryResponse.toList(it) }
        }
}
