package com.tayler.valushopping.ui.profile

import com.tayler.entity.UserModel
import com.tayler.usecases.AppUseCase
import com.tayler.valushopping.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class UserViewModel @Inject constructor(
    private val appPreferences: AppUseCase
) : BaseViewModel() {

    private val _successUserState = MutableStateFlow<UserModel?>(null)
    val successUserState: StateFlow<UserModel?> = _successUserState.asStateFlow()

    fun loadUser() {
        executeState(stateFlow = _successUserState) {
            appPreferences.getUser()
        }
    }

    fun saveUser(user: UserModel) {
        execute {
            val response = appPreferences.saveUser(user)
            _successUserState.value = response
        }
    }
}