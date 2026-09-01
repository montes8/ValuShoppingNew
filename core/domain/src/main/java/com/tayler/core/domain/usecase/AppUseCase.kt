package com.tayler.core.domain.usecase

import com.tayler.core.model.ParamModel
import com.tayler.core.model.UserBlockingModel
import com.tayler.core.model.UserModel
import com.tayler.core.network.BuildConfig
import com.tayler.core.network.protocol.IConfigNetwork
import com.tayler.core.network.protocol.IUserNetwork
import com.tayler.core.database.preferences.IAppPreferences
import javax.inject.Inject
import java.util.UUID

class AppUseCase @Inject constructor(
    private val appPreferences: IAppPreferences,
    private val userNetwork: IUserNetwork,
    private val configNetwork: IConfigNetwork,
) {

    suspend fun paramInit(imei: String, code: String): ParamModel {
        if (appPreferences.getUUID().isEmpty()) {
            appPreferences.saveUUID(UUID.randomUUID().toString())
        }
        val response = userNetwork.loadParam(code)
        val responseSecurity = configNetwork.loadBlocking()
        val updatedResponse = response.copy(
            blocking = validateBlocking(responseSecurity, imei),
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

    fun validateBlocking(list: List<UserBlockingModel>, imei: String): Boolean {
        val valeUUID = appPreferences.getUUID()
        return list.any {
            (it.imei == imei) || (it.identifierId == valeUUID)
        }
    }
}
