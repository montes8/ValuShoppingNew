package com.tayler.auth.domain.repository

import com.tayler.core.model.DataLogin

interface AuthRepository {
    suspend fun login(user: String, key: String): DataLogin
}
