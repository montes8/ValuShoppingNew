package com.tayler.core.database.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.tayler.core.database.R
import com.tayler.core.database.preferences.AppPreferences
import com.tayler.core.database.preferences.IAppPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {

    @Singleton
    @Binds
    abstract fun bindAppPreferences(impl: AppPreferences): IAppPreferences

    companion object {
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
}
