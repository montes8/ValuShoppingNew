package com.tayler.valushopping.ui.splash

import android.app.Application
import com.tayler.entity.CategoryModel
import com.tayler.entity.HistoryModel
import com.tayler.entity.ParamModel
import com.tayler.entity.TaskModel
import com.tayler.entity.UserBlockingModel
import com.tayler.usecases.AppUseCase
import com.tayler.usecases.ConfigUseCase
import com.tayler.usecases.UserUseCase
import com.tayler.valushopping.entity.AppDataVale
import com.tayler.valushopping.ui.base.BaseViewModel
import com.tayler.valushopping.utils.DEFAULT_TEXT_WELCOME
import com.tayler.valushopping.utils.TY_DEFAULT
import com.valu.uitaycompose.utils.extension.uiTayCountryNetwork
import com.valu.uitaycompose.utils.extension.uiTayDateToString
import com.valu.uitaycompose.utils.extension.uiTayGetMobilIPAddress
import com.valu.uitaycompose.utils.extension.uiTayLoadImei
import com.valu.uitaycompose.utils.extension.uiTayNumberPhone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val configUseCase: ConfigUseCase,
    private val appUseCase: AppUseCase,
    private val application: Application
) : BaseViewModel() {

    private val _successParamState = MutableStateFlow<ParamModel?>(null)
    val successParamState: StateFlow<ParamModel?> = _successParamState.asStateFlow()

    private val _successUpdateParamState = MutableStateFlow<ParamModel?>(null)
    val successUpdateParamState: StateFlow<ParamModel?> = _successUpdateParamState.asStateFlow()

    private val _successListTaskState = MutableStateFlow<List<TaskModel>>(emptyList())
    val successListTaskState: StateFlow<List<TaskModel>> = _successListTaskState.asStateFlow()


    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    fun loadSplashData() {
        execute(false) {
            val welcome = io {
                AppDataVale.styleValu = appUseCase.urlImage()
                AppDataVale.styleValu = appUseCase.getStyle()
                AppDataVale.bgService = appUseCase.getBgService()
                AppDataVale.session =  appUseCase.getToken()
                AppDataVale.categories = configUseCase.listCategories()
                AppDataVale.categoriesAll = configUseCase.listCategoriesAll()
                appUseCase.getTexWelcome().ifEmpty { DEFAULT_TEXT_WELCOME }
            }

        _uiState.value = SplashUiState(
            welcomeText = welcome,
            textColor = AppDataVale.getColorPrincipal().first,
            showLogo = AppDataVale.bgService
        )

          updateUiState { currentState ->
                currentState.copy(
                    statusBarColor = AppDataVale.getColorPrincipal().second
                )
          }
      }
    }

    fun getFlagIcon(): String {
        val iconId = appUseCase.geIdIcon()
        return iconId.ifEmpty { "Principal" }
    }

    fun getFlagIconOld(): String {
        val iconId = appUseCase.geIdIconOld()
        return iconId.ifEmpty { "Principal" }
    }

    fun saveFlagIcon(id: String) {
        appUseCase.saveIdIcon(id)
    }

    fun saveFlagIconOld(id: String) {
        appUseCase.saveIdIconOld(id)
    }

    fun loadParam() {
        execute(false) {
            val res = io {
                if (appUseCase.getUUID().isEmpty()) {
                    appUseCase.saveUUID(UUID.randomUUID().toString())
                }
                val response = userUseCase.loadParam(application.uiTayCountryNetwork())
                val responseSecurity = configUseCase.loadBlocking()

                appUseCase.saveStyle(response.styleValu ?: TY_DEFAULT)
                appUseCase.saveIdIcon(response.idIcon ?: "Principal")
                response.blocking = validateBlocking(responseSecurity)
                AppDataVale.user = appUseCase.getUser()
                AppDataVale.styleValu = response.styleValu ?: TY_DEFAULT
                response.textWelcome?.let { appUseCase.saveTexWelcome(it) }
                response.bgService?.let { appUseCase.saveBgService(it) }
                response
            }

            _successParamState.emit(res)
        }
    }

    fun loadListTask() {
        execute(false) {
            val response = configUseCase.listTask()
            _successListTaskState.value = response
        }
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

    fun updateParam(param: ParamModel) {
        execute(false) {
            val response = if (param.uid?.isEmpty() == true)
                userUseCase.saveParam(param) else
                userUseCase.updateParam(param)
            _successUpdateParamState.value = response
        }
    }

    private fun validateBlocking(list: List<UserBlockingModel>): Boolean {
        val valeId = application.uiTayLoadImei()
        val valeUUID = appUseCase.getUUID()
        val numberPhone = application.uiTayNumberPhone()
        list.forEach {
            if (it.imei == valeId || it.identifierId == valeUUID || numberPhone == it.ipAddress) {
                return true
            }
        }
        return false
    }

}