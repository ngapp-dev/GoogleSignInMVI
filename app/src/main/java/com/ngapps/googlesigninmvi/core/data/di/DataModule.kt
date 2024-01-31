package com.ngapps.googlesigninmvi.core.data.di

import com.ngapps.googlesigninmvi.core.data.repository.AuthRepository
import com.ngapps.googlesigninmvi.core.data.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {


    @Binds
    fun bindsAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl,
    ): AuthRepository

}
