package com.ngapps.googlesigninmvi.core.datastore.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.ngapps.googlesigninmvi.core.datastore.AuthDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private const val SHARED_PREFS_FILE_NAME = "token"
    private const val ENCRYPTED_SHARED_PREFS_FILE_NAME = "token_secured"

    @Singleton
    @Provides
    fun provideMasterKeyAlias(application: Application): MasterKey {
        return MasterKey.Builder(application, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

    }

    @Singleton
    @Provides
    fun provideEncryptedSharedPreferences(
        application: Application,
        masterKeyAlias: MasterKey
    ): EncryptedSharedPreferences {
        return EncryptedSharedPreferences.create(
            application,
            ENCRYPTED_SHARED_PREFS_FILE_NAME,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences
    }

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            SHARED_PREFS_FILE_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideAuthTokensDataSource(
        sharedPreferences: SharedPreferences,
        encryptedSharedPreferences: EncryptedSharedPreferences
    ): AuthDataSource {
        return AuthDataSource(sharedPreferences, encryptedSharedPreferences)
    }
}
