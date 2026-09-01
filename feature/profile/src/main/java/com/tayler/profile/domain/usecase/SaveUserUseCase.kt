package com.tayler.profile.domain.usecase

import com.tayler.core.model.UserModel
import com.tayler.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(user: UserModel): UserModel {
        return repository.saveUser(user)
    }
}
