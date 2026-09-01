package com.tayler.auth.data.repository

import com.tayler.auth.data.api.AuthApiService
import com.tayler.auth.data.api.model.LoginRequest
import com.tayler.auth.data.api.model.LoginResponse
import com.tayler.auth.domain.repository.AuthRepository
import com.tayler.core.model.DataLogin
import com.tayler.core.network.base.BaseNetwork
import com.tayler.core.network.utils.processResponse
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val base: BaseNetwork
) : AuthRepository {

    override suspend fun login(user: String, key: String): DataLogin = base.safeApiCall {
        authApiService.login(LoginRequest(user, key)).processResponse { LoginResponse.toModel(it) }
    }
}
