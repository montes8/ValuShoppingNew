package com.tayler.detail.data.di

import com.tayler.detail.data.api.DetailApiService
import com.tayler.detail.data.repository.DetailRepositoryImpl
import com.tayler.detail.domain.repository.DetailRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailDataModule {

    @Binds
    @Singleton
    abstract fun bindDetailRepository(impl: DetailRepositoryImpl): DetailRepository

    companion object {
        @Provides
        @Singleton
        fun provideDetailApiService(retrofit: Retrofit): DetailApiService {
            return retrofit.create(DetailApiService::class.java)
        }
    }
}
