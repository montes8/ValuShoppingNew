package com.tayler.repository.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.tayler.entity.exception.UiTayApiException
import com.tayler.repository.R
import com.tayler.repository.network.ServiceApi
import com.tayler.repository.preferences.manager.PreferencesManager
import com.tayler.repository.utils.AUTHORIZATION
import com.tayler.repository.utils.MY_CONTENT_TYPE
import com.tayler.repository.utils.MY_TIME_ON
import com.tayler.repository.utils.PLATFORM
import com.tayler.repository.utils.PREFERENCE_TOKEN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Inject
import jakarta.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.tayler.repository.BuildConfig
import okhttp3.CertificatePinner
import java.net.URL

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {

    @Singleton
    @Provides
    fun providerSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        val fileName = context.resources.getString(R.string.encryption_key)
        return try {
            createEncryptedSharedPreferences(context, fileName)
        } catch (e: Exception) {
            e.printStackTrace()
            // Si hay un error de corrupción (como el que estás viendo), borramos el archivo y reintentamos
            context.deleteSharedPreferences(fileName)
            createEncryptedSharedPreferences(context, fileName)
        }
    }

    private fun createEncryptedSharedPreferences(context: Context, fileName: String): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            fileName,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL


    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        )
    }

    @Singleton
    @Provides
    fun provideCertificatePinning(): CertificatePinner {
        if (BuildConfig.DEBUG) {
            return CertificatePinner.DEFAULT
        }
        val host = URL(BuildConfig.BASE_URL).host
        return CertificatePinner.Builder()
            .add(host, BuildConfig.PINNIG)
            .add(host, BuildConfig.PINNIG_ROOT)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        apiInterceptor: Interceptor,
        certificatePinning: CertificatePinner
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(MY_TIME_ON, TimeUnit.SECONDS)
            .writeTimeout(MY_TIME_ON, TimeUnit.SECONDS)
            .readTimeout(MY_TIME_ON, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiInterceptor)
            .certificatePinner(certificatePinning)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideACMService(retrofit: Retrofit): ServiceApi = retrofit.create(ServiceApi::class.java)


    @Singleton
    @Provides
    fun providerHeaderInterceptor(preferencesManager: PreferencesManager): Interceptor {
        return ApiInterceptor(preferencesManager)
    }
}

class ApiInterceptor @Inject constructor(private val preferencesManager: PreferencesManager) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val builder = request.newBuilder()
            .addHeader("Content-Type", MY_CONTENT_TYPE)
            .header("x-os", PLATFORM)
        if (preferencesManager.getString(PREFERENCE_TOKEN).isNotEmpty()) {
            builder.addHeader(AUTHORIZATION, preferencesManager.getString(PREFERENCE_TOKEN))
        }
        request = builder.build()
        return chain.proceed(request)
    }
}
