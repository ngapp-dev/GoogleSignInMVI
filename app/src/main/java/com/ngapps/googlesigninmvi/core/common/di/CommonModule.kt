package com.ngapps.googlesigninmvi.core.common.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    const val CLIENT_ID = "PUT_YOUR_CLIENT_ID_HERE"

    @Provides
    fun provideGoogleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(CLIENT_ID)
            .requestEmail()
            .build()
    }

    @Provides
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context,
        googleSignInOptions: GoogleSignInOptions
    ): GoogleSignInClient {
        return GoogleSignIn.getClient(context, googleSignInOptions)
    }
}