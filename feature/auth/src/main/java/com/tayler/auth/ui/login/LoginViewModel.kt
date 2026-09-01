package com.tayler.auth.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tayler.auth.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    fun login(user: String, key: String) {
        viewModelScope.launch {
            try {
                val result = loginUseCase(user, key)
                // Handle success
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
