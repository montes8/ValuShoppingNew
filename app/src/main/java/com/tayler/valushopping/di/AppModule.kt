package com.tayler.valushopping.di

import com.tayler.valushopping.entity.AppDataVale
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDataVale(): AppDataVale = AppDataVale()
}
