package com.tayler.repository.network

import com.tayler.repository.network.model.request.HistoryRequest
import com.tayler.repository.network.model.request.LoginRequest
import com.tayler.repository.network.model.request.ProductImageRequest
import com.tayler.repository.network.model.response.CategoryResponse
import com.tayler.repository.network.model.response.ImageMoreResponse
import com.tayler.repository.network.model.response.ImageResponse
import com.tayler.repository.network.model.response.LoginResponse
import com.tayler.repository.network.model.response.ParamResponse
import com.tayler.repository.network.model.response.ProductResponse
import com.tayler.repository.network.model.response.TaskResponse
import com.tayler.repository.network.model.response.UserBlockingResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ServiceApi {

    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("config/param/{id}")
    suspend fun loadParam( @Path("id") id: String): Response<ParamResponse>

    @POST("config/param")
    suspend fun saveParam(@Body paramResponse: ParamResponse): Response<ParamResponse>

    @PUT("config/param/{id}")
    suspend fun updateParam(
        @Path("id") id: String,
        @Body paramResponse: ParamResponse
    ): Response<ParamResponse>

    @Multipart
    @POST("uploads/product/{nameFile}")
    suspend fun saveImage(
        @Part file: MultipartBody.Part,
        @Path("nameFile") id: String
    ): Response<ImageResponse>

    @Multipart
    @POST("uploads/productMore/{nameFile}")
    suspend fun saveImageMore(
        @Part file: MultipartBody.Part,
        @Path("nameFile") id: String
    ): Response<ImageResponse>

    @POST("product")
    suspend fun saveProduct(@Body productResponse: ProductResponse): Response<ProductResponse>


    @GET("product/{id}")
    suspend fun loadProduct(@Path("id") id: String): Response<List<ProductResponse>>

    @GET("product/category/{id}")
    suspend fun loadProductCategory(@Path("id") id: String): Response<List<ProductResponse>>


    @GET("product/all/{id}")
    suspend fun loadProducts(@Path("id") id: String): Response<List<ProductResponse>>

    @DELETE("product/{id}")
    suspend fun deleteProduct(@Path("id") id: String): Response<ProductResponse>

    @PUT("product")
    suspend fun updateProduct(@Body productResponse: ProductResponse): Response<ProductResponse>

    @GET("product/img/{id}")
    suspend fun loadProductImage(@Path("id") id: String): Response<List<ImageMoreResponse>>

    @POST("product/img")
    suspend fun saveProductImages(@Body productResponse: ProductImageRequest): Response<ImageMoreResponse>

    @DELETE("product/img/{id}")
    suspend fun deleteProductImage(@Path("id") id: String): Response<ImageMoreResponse>


    @GET("config/blocking")
    suspend fun loadUserBlocking(): Response<List<UserBlockingResponse>>

    @POST("config/history")
    suspend fun saveHistory(@Body historyRequest: HistoryRequest): Response<HistoryRequest>

    @GET("config/category")
    suspend fun loadCategories(): Response<List<CategoryResponse>>

    @GET("config/category/all")
    suspend fun loadCategoriesAll(): Response<List<CategoryResponse>>

    @GET("config/taskValu")
    suspend fun loadTaskValu(): Response<List<TaskResponse>>

}