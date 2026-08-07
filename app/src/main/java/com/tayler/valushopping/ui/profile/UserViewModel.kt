package com.tayler.valushopping.ui.profile

import com.tayler.entity.UserModel
import com.tayler.usecases.AppUseCase
import com.tayler.usecases.UserUseCase
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.ui.base.BaseViewModel
import com.valu.uitaycompose.utils.UI_EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class UserViewModel @Inject constructor(
    private val appPreferences: AppUseCase,
    private val userNetwork: UserUseCase
) : BaseViewModel() {

    private val _successUserState = MutableStateFlow<UserModel?>(null)
    val successUserState: StateFlow<UserModel?> = _successUserState.asStateFlow()

    private val _successUserImgState = MutableStateFlow<UserModel?>(null)
    val successUserImgState: StateFlow<UserModel?> = _successUserImgState.asStateFlow()

    private val _successLoginState = MutableStateFlow<Boolean?>(null)
    val successLoginState: StateFlow<Boolean?> = _successLoginState.asStateFlow()

    fun loadUser() {
        executeState(stateFlow = _successUserState) {
            appPreferences.getUser()
        }
    }
    fun loadSession() = appPreferences.getToken()

    fun login(user: String, key: String) {
        execute {
            val response = userNetwork.login(user, key)
            appPreferences.saveToken(response.token)
            mapperUserUpdate(response.userValid ?: UserModel())
            _successLoginState.value = true
        }
    }

    private fun mapperUserUpdate(response: UserModel): UserModel {
        val useSave = appPreferences.getUser()
        useSave.uid = response.uid
        useSave.rol = response.rol
        useSave.nameUser = response.nameUser
        useSave.names = response.names
        useSave.lastName = response.lastName
        useSave.document = response.document
        useSave.email = response.email
        useSave.phone = response.phone
        useSave.address = response.address
        useSave.countryCode = response.countryCode
        useSave.district = response.district
        useSave.deliveryPoint = response.deliveryPoint
        useSave.latitude = response.latitude
        useSave.longitude = response.longitude
        useSave.limitDistance = response.limitDistance
        useSave.limitProductAdd = response.limitProductAdd
        useSave.addMoreImage = response.addMoreImage
        useSave.addPrincipal = response.addPrincipal
        useSave.sellerClient = response.sellerClient
        val userUpdate = appPreferences.saveUser(useSave)
        AppDataVale.user = userUpdate
        return useSave
    }

    fun saveUser(user: UserModel) {
        execute {
            val response = appPreferences.saveUser(user)
            _successUserState.value = response
        }
    }

    fun saveUserImg(user: UserModel) {
        execute {
            val response = appPreferences.saveUser(user)
            _successUserImgState.value = response
        }
    }

    fun logout() {
        execute(false) {
            appPreferences.saveToken(UI_EMPTY)
        }
    }
}