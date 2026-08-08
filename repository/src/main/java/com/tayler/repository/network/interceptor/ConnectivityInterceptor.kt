package com.tayler.repository.network.interceptor

import android.content.Context
import com.tayler.entity.exception.MyNetworkException
import com.tayler.repository.utils.isAirplaneModeActive
import com.tayler.repository.utils.isConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ConnectivityInterceptor @Inject constructor(
    @param:ApplicationContext private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isConnected() || context.isAirplaneModeActive()) {
            throw MyNetworkException()
        }
        return chain.proceed(chain.request())
    }
}
