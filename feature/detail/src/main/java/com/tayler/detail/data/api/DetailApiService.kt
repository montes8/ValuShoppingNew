package com.tayler.detail.data.api

import com.tayler.detail.data.api.model.ImageMoreResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailApiService {
    @GET("product/img/{id}")
    suspend fun loadProductImage(@Path("id") id: String): Response<List<ImageMoreResponse>>
}
