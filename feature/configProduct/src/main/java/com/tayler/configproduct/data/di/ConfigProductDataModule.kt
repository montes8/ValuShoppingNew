package com.tayler.configproduct.data.di

import com.tayler.configproduct.data.api.ConfigProductApiService
import com.tayler.configproduct.data.repository.ConfigProductRepositoryImpl
import com.tayler.configproduct.domain.repository.ConfigProductRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConfigProductDataModule {

    @Binds
    @Singleton
    abstract fun bindConfigProductRepository(impl: ConfigProductRepositoryImpl): ConfigProductRepository

    companion object {
        @Provides
        @Singleton
        fun provideConfigProductApiService(retrofit: Retrofit): ConfigProductApiService {
            return retrofit.create(ConfigProductApiService::class.java)
        }
    }
}
