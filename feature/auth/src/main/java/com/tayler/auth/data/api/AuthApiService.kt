package com.tayler.auth.data.api

import com.tayler.auth.data.api.model.LoginRequest
import com.tayler.auth.data.api.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
