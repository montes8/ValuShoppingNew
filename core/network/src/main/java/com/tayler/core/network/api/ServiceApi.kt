package com.tayler.core.network.api

import com.tayler.core.model.QuantumPublicKeyResponse
import com.tayler.core.model.SecurityAlertRequest
import com.tayler.core.network.data.api.model.HistoryRequest
import com.tayler.core.network.data.api.model.ParamResponse
import com.tayler.core.network.data.api.model.UserBlockingResponse
import com.tayler.core.network.data.api.model.CategoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServiceApi {

    @GET("config/param/{id}")
    suspend fun loadParam(@Path("id") id: String): Response<ParamResponse>

    @POST("config/history")
    suspend fun saveHistory(@Body historyRequest: HistoryRequest): Response<HistoryRequest>

    @GET("config/blocking")
    suspend fun loadUserBlocking(): Response<List<UserBlockingResponse>>

    @GET("config/category")
    suspend fun loadCategories(): Response<List<CategoryResponse>>

    @GET("config/category/all")
    suspend fun loadCategoriesAll(): Response<List<CategoryResponse>>

    @GET("config/public-key")
    suspend fun getQuantumPublicKey(): Response<QuantumPublicKeyResponse>

    @POST("security")
    suspend fun sendSecurityAlert(@Body request: SecurityAlertRequest): Response<Unit>
}
