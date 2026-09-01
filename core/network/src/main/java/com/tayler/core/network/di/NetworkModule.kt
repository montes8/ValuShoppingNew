package com.tayler.core.network.di

import com.tayler.core.common.utils.MY_TIME_ON
import com.tayler.core.database.preferences.PreferencesManager
import com.tayler.core.network.BuildConfig
import com.tayler.core.network.api.ConfigNetwork
import com.tayler.core.network.api.DataNetwork
import com.tayler.core.network.api.QuantumNetwork
import com.tayler.core.network.api.ServiceApi
import com.tayler.core.network.api.UserNetwork
import com.tayler.core.network.interceptor.ApiInterceptor
import com.tayler.core.network.interceptor.ConnectivityInterceptor
import com.tayler.core.network.protocol.IConfigNetwork
import com.tayler.core.network.protocol.IDataNetwork
import com.tayler.core.network.protocol.IQuantumNetwork
import com.tayler.core.network.protocol.IUserNetwork
import com.tayler.core.network.quantum.QuantumConverterFactory
import com.tayler.core.network.quantum.QuantumSecurityManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindQuantumNetwork(impl: QuantumNetwork): IQuantumNetwork

    @Binds
    @Singleton
    abstract fun bindConfigNetwork(impl: ConfigNetwork): IConfigNetwork

    @Binds
    @Singleton
    abstract fun bindDataNetwork(impl: DataNetwork): IDataNetwork

    @Binds
    @Singleton
    abstract fun bindUserNetwork(impl: UserNetwork): IUserNetwork

    companion object {
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
                .retryOnConnectionFailure(true)
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
}
