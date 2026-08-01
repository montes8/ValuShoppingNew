package com.tayler.repository.network.base

import android.content.Context
import com.tayler.entity.exception.MyNetworkException
import com.tayler.repository.utils.isAirplaneModeActive
import com.tayler.repository.utils.isConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

open class BaseNetwork @Inject constructor(
    @param:ApplicationContext val context: Context
) {
    suspend fun <T> executeWithConnection(validHour: Boolean = true, block: suspend () -> T): T {
        if (!context.applicationContext.isConnected() || context.applicationContext.isAirplaneModeActive()) {
            throw MyNetworkException()
        }
        return block()
    }
}