package com.tayler.home.data.api

import com.tayler.home.data.api.model.CategoryResponse
import com.tayler.home.data.api.model.ProductResponse
import com.tayler.home.data.api.model.UserBlockingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeApiService {

    @GET("product/{id}")
    suspend fun loadProduct(@Path("id") id: String): Response<List<ProductResponse>>

    @GET("product/category/{id}")
    suspend fun loadProductCategory(@Path("id") id: String): Response<List<ProductResponse>>

    @GET("product/all/{id}")
    suspend fun loadProducts(@Path("id") id: String): Response<List<ProductResponse>>

    @GET("config/category")
    suspend fun loadCategories(): Response<List<CategoryResponse>>

    @GET("config/category/all")
    suspend fun loadCategoriesAll(): Response<List<CategoryResponse>>
    
    @GET("config/blocking")
    suspend fun loadUserBlocking(): Response<List<UserBlockingResponse>>
}
