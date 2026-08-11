package com.tayler.valushopping.ui.profile

import androidx.lifecycle.viewModelScope
import com.tayler.entity.UserModel
import com.tayler.usecases.AppUseCase
import com.tayler.valushopping.di.IoDispatcher
import com.tayler.valushopping.ui.base.BaseViewModel
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import com.valu.uitaycompose.utils.extension.uiTayValidateEmail
import com.valu.uitaycompose.utils.extension.uiTayValidatePhoneFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/*** o podriamos usar combien solo que despues de 5 parametros se hace complejo
 * val isFormValid: StateFlow<Boolean> = listOf(
 *     nameFlow,
 *     lastNameFlow,
 *     documentFlow,
 *     emailFlow,
 *     phoneFlow,
 *     addressFlow
 * ).combine { values ->
 *     // values es un Array<String> en el orden exacto en que los pusiste en la lista
 *     val name = values[0]
 *     val lastName = values[1]
 *     val document = values[2]
 *     val email = values[3]
 *     val phone = values[4]
 *     val address = values[5]
 *
 *     val isValidName = name.isNotEmpty()
 *     val isValidLastName = lastName.isNotEmpty()
 *     val isValidDoc = document.length == 8
 *     val isValidEmail = email.uiTayValidateEmail()
 *     val isValidPhone = phone.uiTayValidatePhoneFormat()
 *     val isValidAddress = address.isNotEmpty()
 *
 *     isValidName && isValidLastName && isValidDoc && isValidEmail && isValidPhone && isValidAddress
 * }.stateIn(
 *     scope = viewModelScope,
 *     started = SharingStarted.WhileSubscribed(5000),
 *     initialValue = false
 * )
 **/

@HiltViewModel
class UserViewModel @Inject constructor(
    private val appPreferences: AppUseCase,
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
            val loadedUser = io { appPreferences.getUser() }
            _formState.value = loadedUser
        }
    }

    fun saveUser(user: UserModel) {
        execute(globalUiStateManager = globalUiStateManager) {
            val response = io { appPreferences.saveUser(user) }
            _formState.value = response
        }
    }

    fun saveUserImg(user: UserModel) {
        execute(globalUiStateManager = globalUiStateManager) {
            val response = io { appPreferences.saveUser(user) }
            _formState.value = response
        }
    }
}