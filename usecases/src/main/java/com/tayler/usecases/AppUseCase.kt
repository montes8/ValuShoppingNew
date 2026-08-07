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
    private val iAppPreferences: IAppPreferences,
    private val iUserNetwork: IUserNetwork,
    private val iConfigNetwork: IConfigNetwork,
) {

    suspend fun paramInit(imei: String, number: String, code: String): ParamModel {
        if (iAppPreferences.getUUID().isEmpty()) {
            iAppPreferences.saveUUID(UUID.randomUUID().toString())
        }
        val response = iUserNetwork.loadParam(code)
        val responseSecurity = iConfigNetwork.loadBlocking()
        val updatedResponse = response.copy(
            blocking = validateBlocking(responseSecurity, imei, number)
        )
        iAppPreferences.saveParaDb(updatedResponse)
        return updatedResponse
    }

    fun configInitParam(): ParamModel {
        if (iAppPreferences.getUUID().isEmpty()) {
            iAppPreferences.saveUUID(UUID.randomUUID().toString())
        }
        val param = iAppPreferences.getParaDb()
        return param.copy(
            session = iAppPreferences.getToken(),
            urlImage = BuildConfig.BASE_URL
        )
    }

    fun saveParam(model: ParamModel) {
        iAppPreferences.saveParaDb(model)
    }

    fun getUUID(): String {
        return iAppPreferences.getUUID()
    }

    fun saveUser(value: UserModel): UserModel {
        return iAppPreferences.saveUser(value)
    }

    fun getUser(): UserModel {
        return iAppPreferences.getUser()
    }

    fun validateBlocking(list: List<UserBlockingModel>, imei: String, number: String): Boolean {
        val valeUUID = iAppPreferences.getUUID()
        list.forEach {
            if (it.imei == imei || it.identifierId == valeUUID || number == it.ipAddress) {
                return true
            }
        }
        return false
    }
}
