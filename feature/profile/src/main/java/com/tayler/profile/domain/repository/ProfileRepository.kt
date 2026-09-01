package com.tayler.profile.domain.repository

import com.tayler.core.model.UserModel

interface ProfileRepository {
    suspend fun getUser(): UserModel
    suspend fun saveUser(user: UserModel): UserModel
}
