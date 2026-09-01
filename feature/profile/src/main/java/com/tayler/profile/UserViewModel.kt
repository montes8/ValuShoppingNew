package com.tayler.profile

import androidx.lifecycle.viewModelScope
import com.tayler.core.model.UserModel
import com.tayler.profile.domain.usecase.GetUserUseCase
import com.tayler.profile.domain.usecase.SaveUserUseCase
import com.tayler.ui.di.IoDispatcher
import com.tayler.ui.ui.base.BaseViewModel
import com.tayler.ui.ui.base.GlobalUiStateManager
import com.valu.uitaycompose.utils.extension.uiTayValidateEmail
import com.valu.uitaycompose.utils.extension.uiTayValidatePhoneFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val globalUiStateManager: GlobalUiStateManager,
    @IoDispatcher ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel(ioDispatcher) {

    private val _formState = MutableStateFlow(UserModel())
    val formState: StateFlow<UserModel> = _formState.asStateFlow()

    val isFormValid: StateFlow<Boolean> = _formState
        .map { user ->
            val isValidName = user.names.isNotEmpty()
            val isValidLastName = user.lastName.isNotEmpty()
            val isValidDoc = user.document.length == 8
            val isValidEmail = user.email.uiTayValidateEmail()
            val isValidPhone = user.phone.uiTayValidatePhoneFormat()
            val isValidAddress = user.address.isNotEmpty()

            isValidName && isValidLastName && isValidDoc && isValidEmail && isValidPhone && isValidAddress
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun onNameChanged(newName: String) { _formState.value = _formState.value.copy(names = newName) }
    fun onLastNameChanged(newLastName: String) { _formState.value = _formState.value.copy(lastName = newLastName) }
    fun onDocumentChanged(newDocument: String) { _formState.value = _formState.value.copy(document = newDocument) }
    fun onEmailChanged(newEmail: String) { _formState.value = _formState.value.copy(email = newEmail) }
    fun onPhoneChanged(newPhone: String) { _formState.value = _formState.value.copy(phone = newPhone) }
    fun onAddressChanged(newAddress: String) { _formState.value = _formState.value.copy(address = newAddress) }

    fun loadUser() {
        execute(globalUiStateManager = globalUiStateManager) {
            val loadedUser = io { getUserUseCase() }
            _formState.value = loadedUser
        }
    }

    fun saveUser(user: UserModel) {
        execute(globalUiStateManager = globalUiStateManager) {
            val response = io { saveUserUseCase(user) }
            _formState.value = response
        }
    }

    fun saveUserImg(user: UserModel) {
        execute(globalUiStateManager = globalUiStateManager) {
            val response = io { saveUserUseCase(user) }
            _formState.value = response
        }
    }
}
