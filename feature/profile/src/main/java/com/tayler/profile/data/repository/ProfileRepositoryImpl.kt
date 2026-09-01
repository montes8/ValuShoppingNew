package com.tayler.profile.data.repository

import com.tayler.core.model.UserModel
import com.tayler.core.database.preferences.IAppPreferences
import com.tayler.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val appPreferences: IAppPreferences
) : ProfileRepository {
    override suspend fun getUser(): UserModel {
        return appPreferences.getUser()
    }

    override suspend fun saveUser(user: UserModel): UserModel {
        return appPreferences.saveUser(user)
    }
}
