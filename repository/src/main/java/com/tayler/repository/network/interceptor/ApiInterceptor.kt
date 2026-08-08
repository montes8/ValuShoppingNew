package com.tayler.repository.network.interceptor

import com.tayler.repository.preferences.manager.PreferencesManager
import com.tayler.repository.utils.AUTHORIZATION
import com.tayler.repository.utils.MY_CONTENT_TYPE
import com.tayler.repository.utils.PLATFORM
import com.tayler.repository.utils.PREFERENCE_TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiInterceptor @Inject constructor(
    private val preferencesManager: PreferencesManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
            .addHeader("Content-Type", MY_CONTENT_TYPE)
            .header("x-os", PLATFORM)
        
        val token = preferencesManager.getString(PREFERENCE_TOKEN)
        if (token.isNotEmpty()) {
            builder.addHeader(AUTHORIZATION, token)
        }
        
        return chain.proceed(builder.build())
    }
}
