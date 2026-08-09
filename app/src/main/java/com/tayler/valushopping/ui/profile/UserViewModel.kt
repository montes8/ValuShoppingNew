package com.tayler.valushopping.ui.profile

import com.tayler.entity.UserModel
import com.tayler.usecases.AppUseCase
import com.tayler.valushopping.di.IoDispatcher
import com.tayler.valushopping.ui.base.BaseViewModel
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class UserViewModel @Inject constructor(
    private val appPreferences: AppUseCase,
    private val globalUiStateManager: GlobalUiStateManager,
    @IoDispatcher ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel(ioDispatcher) {

    private val _successUserState = MutableStateFlow<UserModel?>(null)
    val successUserState: StateFlow<UserModel?> = _successUserState.asStateFlow()


    fun loadUser() {
        executeState(stateFlow = _successUserState, globalUiStateManager = globalUiStateManager) {
            appPreferences.getUser()
        }
    }

    fun saveUser(user: UserModel) {
        execute(globalUiStateManager = globalUiStateManager) {
            val response = appPreferences.saveUser(user)
            _successUserState.value = response
        }
    }

    fun saveUserImg(user: UserModel) {
        execute(globalUiStateManager = globalUiStateManager) {
             appPreferences.saveUser(user)
        }
    }
}