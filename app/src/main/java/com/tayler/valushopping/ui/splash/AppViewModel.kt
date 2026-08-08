package com.tayler.valushopping.ui.splash

import android.app.Application
import android.util.Log
import com.tayler.entity.HistoryModel
import com.tayler.entity.ParamModel
import com.tayler.usecases.AppUseCase
import com.tayler.usecases.ConfigUseCase
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.ui.base.BaseViewModel
import com.valu.uitaycompose.utils.extension.uiTayCountryNetwork
import com.valu.uitaycompose.utils.extension.uiTayDateToString
import com.valu.uitaycompose.utils.extension.uiTayGetMobilIPAddress
import com.valu.uitaycompose.utils.extension.uiTayLoadImei
import com.valu.uitaycompose.utils.extension.uiTayNumberPhone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.tayler.valushopping.ui.base.GlobalUiStateManager
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val configUseCase: ConfigUseCase,
    val appUseCase: AppUseCase,
    private val application: Application,
    private val appDataVale: AppDataVale,
    private val globalUiStateManager: GlobalUiStateManager
) : BaseViewModel() {

    private val _successParamState = MutableStateFlow<ParamModel?>(null)
    val successParamState: StateFlow<ParamModel?> = _successParamState.asStateFlow()

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    fun initSplash() {
        execute(loading = false, globalUiStateManager = globalUiStateManager) {
            setupInitialUiState()
            loadRemoteParams()
        }
    }

    private suspend fun setupInitialUiState() {
        val paramInit = io { appUseCase.configInitParam() }
        appDataVale.paramData = paramInit
        Log.d("servicedata","${appDataVale.paramData}")
        _uiState.value = SplashUiState(
            welcomeText = appDataVale.paramData.textWelcome,
            textColor = appDataVale.getColorPrincipal().first,
            showLogo = appDataVale.paramData.bgService
        )
        globalUiStateManager.updateUiState { currentState ->
            currentState.copy(statusBarColor = appDataVale.getColorPrincipal().second)
        }
    }

    private suspend fun loadRemoteParams() {
        val paramResponse = io {
            coroutineScope {
                val paramDeferred = async {
                    appUseCase.paramInit(
                        application.uiTayLoadImei(),
                        application.uiTayNumberPhone(),
                        application.uiTayCountryNetwork()
                    )
                }
                val userDeferred = async { appUseCase.getUser() }
                val categoriesAllDeferred = async { configUseCase.listCategoriesAll() }

                appDataVale.user = userDeferred.await()
                appDataVale.categoriesAll = categoriesAllDeferred.await()
                paramDeferred.await()
            }
        }
        _successParamState.emit(paramResponse)
    }

    fun saveHistory(typeFlow: String) {
        execute(false) {
            configUseCase.saveHistory(
                HistoryModel(
                    type = typeFlow,
                    name = "${appDataVale.user.names} ${appDataVale.user.lastName}",
                    appDataVale.latitude,
                    appDataVale.longitude,
                    appDataVale.user.address,
                    application.uiTayLoadImei(),
                    appUseCase.getUUID(),
                    Date().uiTayDateToString(),
                    Date().uiTayDateToString("hh:mm a"),
                    uiTayGetMobilIPAddress(),
                    application.uiTayNumberPhone()
                )
            )
        }
    }
}