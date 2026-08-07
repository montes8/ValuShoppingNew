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
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val configUseCase: ConfigUseCase,
    private val appUseCase: AppUseCase,
    private val application: Application
) : BaseViewModel() {

    private val _successParamState = MutableStateFlow<ParamModel?>(null)
    val successParamState: StateFlow<ParamModel?> = _successParamState.asStateFlow()

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    fun initSplash() {
        execute(false) {
            setupInitialUiState()
            loadRemoteParams()
        }
    }

    private suspend fun setupInitialUiState() {
        val paramInit = io { appUseCase.configInitParam() }
        AppDataVale.paramData = paramInit
        Log.d("servicedata","${AppDataVale.paramData}")
        _uiState.value = SplashUiState(
            welcomeText = AppDataVale.paramData.textWelcome,
            textColor = AppDataVale.getColorPrincipal().first,
            showLogo = AppDataVale.paramData.bgService
        )
        updateUiState { currentState ->
            currentState.copy(statusBarColor = AppDataVale.getColorPrincipal().second)
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

                AppDataVale.user = userDeferred.await()
                AppDataVale.categoriesAll = categoriesAllDeferred.await()
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
                    name = "${AppDataVale.user.names} ${AppDataVale.user.lastName}",
                    AppDataVale.latitude,
                    AppDataVale.longitude,
                    AppDataVale.user.address,
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