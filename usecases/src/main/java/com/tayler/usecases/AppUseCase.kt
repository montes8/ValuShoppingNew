package com.tayler.usecases

import com.tayler.entity.ParamModel
import com.tayler.entity.UserBlockingModel
import com.tayler.entity.UserModel
import com.tayler.repository.BuildConfig
import com.tayler.repository.network.protocol.IConfigNetwork
import com.tayler.repository.network.protocol.IUserNetwork
import com.tayler.repository.preferences.IAppPreferences
import jakarta.inject.Inject
import java.util.UUID

class AppUseCase @Inject constructor(
    private val appPreferences: IAppPreferences,
    private val userNetwork: IUserNetwork,
    private val configNetwork: IConfigNetwork,
) {

    suspend fun paramInit(imei: String, number: String, code: String): ParamModel {
        if (appPreferences.getUUID().isEmpty()) {
            appPreferences.saveUUID(UUID.randomUUID().toString())
        }
        val response = userNetwork.loadParam(code)
        val responseSecurity = configNetwork.loadBlocking()
        val updatedResponse = response.copy(
            blocking = validateBlocking(responseSecurity, imei, number)
        )
        appPreferences.saveParaDb(updatedResponse)
        return updatedResponse
    }

    fun configInitParam(): ParamModel {
        if (appPreferences.getUUID().isEmpty()) {
            appPreferences.saveUUID(UUID.randomUUID().toString())
        }
        val param = appPreferences.getParaDb()
        return param.copy(
            session = appPreferences.getToken(),
            urlImage = BuildConfig.BASE_URL
        )
    }

    fun saveParam(model: ParamModel) {
        appPreferences.saveParaDb(model)
    }

    fun getUUID(): String {
        return appPreferences.getUUID()
    }

    fun saveUser(value: UserModel): UserModel {
        return appPreferences.saveUser(value)
    }

    fun getUser(): UserModel {
        return appPreferences.getUser()
    }

    fun validateBlocking(list: List<UserBlockingModel>, imei: String, number: String): Boolean {
        val valeUUID = appPreferences.getUUID()
        return list.any {
            it.imei == imei || it.identifierId == valeUUID || number == it.ipAddress
        }
    }
}
