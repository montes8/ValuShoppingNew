package com.tayler.repository.di

import com.tayler.repository.network.api.ConfigNetwork
import com.tayler.repository.network.api.DataNetwork
import com.tayler.repository.network.api.UserNetwork
import com.tayler.repository.network.protocol.IConfigNetwork
import com.tayler.repository.network.protocol.IDataNetwork
import com.tayler.repository.network.protocol.IUserNetwork
import com.tayler.repository.preferences.IAppPreferences
import com.tayler.repository.preferences.api.AppPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConfigMyModule {

    @Singleton
    @Binds
    abstract fun provideIAppPreferences(
        appPreferences: AppPreferences
    ): IAppPreferences


    @Singleton
    @Binds
    abstract fun provideIDataNetwork(
        dataNetwork: DataNetwork
    ): IDataNetwork

    @Singleton
    @Binds
    abstract fun provideIUserNetwork(
        userNetwork: UserNetwork
    ): IUserNetwork

    @Singleton
    @Binds
    abstract fun provideConfigNetwork(
        configNetwork: ConfigNetwork
    ): IConfigNetwork

    fun test(){
        //not implement
    }
}