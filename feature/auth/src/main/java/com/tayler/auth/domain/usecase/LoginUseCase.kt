package com.tayler.auth.domain.usecase

import com.tayler.auth.domain.repository.AuthRepository
import com.tayler.core.model.DataLogin
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(user: String, key: String): DataLogin {
        return repository.login(user, key)
    }
}
