package com.tayler.repository.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.tayler.repository.R
import com.tayler.repository.network.ServiceApi
import com.tayler.repository.preferences.manager.PreferencesManager
import com.tayler.repository.utils.MY_TIME_ON
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.tayler.repository.BuildConfig
import com.tayler.repository.network.quantum.QuantumConverterFactory
import com.tayler.repository.network.quantum.QuantumSecurityManager
import okhttp3.CertificatePinner
import java.net.URL
import com.tayler.repository.network.interceptor.ApiInterceptor
import com.tayler.repository.network.interceptor.ConnectivityInterceptor
import javax.inject.Provider

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
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
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
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            },
        )
    }

    @Singleton
    @Provides
    fun provideCertificatePinner(): CertificatePinner {
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
        connectivityInterceptor: ConnectivityInterceptor,
        certificatePinning: CertificatePinner
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(retryOnConnectionFailure = true)
            .connectTimeout(MY_TIME_ON, TimeUnit.SECONDS)
            .writeTimeout(MY_TIME_ON, TimeUnit.SECONDS)
            .readTimeout(MY_TIME_ON, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiInterceptor)
            .addInterceptor(connectivityInterceptor)
            .certificatePinner(certificatePinning)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient, 
        baseUrl: String,
        quantumConverterFactory: QuantumConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(quantumConverterFactory)
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
    fun providerHeaderInterceptor(
        preferencesManager: PreferencesManager,
        quantumSecurityManagerProvider: Provider<QuantumSecurityManager>
    ): Interceptor {
        return ApiInterceptor(preferencesManager, quantumSecurityManagerProvider)
    }
}
