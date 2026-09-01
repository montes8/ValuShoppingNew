package com.tayler.configproduct.data.api

import com.tayler.configproduct.data.api.model.ImageMoreResponse
import com.tayler.configproduct.data.api.model.ImageResponse
import com.tayler.configproduct.data.api.model.ParamResponse
import com.tayler.configproduct.data.api.model.ProductImageRequest
import com.tayler.configproduct.data.api.model.ProductResponse
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

interface ConfigProductApiService {

    @GET("config/param/{id}")
    suspend fun loadParam(@Path("id") id: String): Response<ParamResponse>

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
}
