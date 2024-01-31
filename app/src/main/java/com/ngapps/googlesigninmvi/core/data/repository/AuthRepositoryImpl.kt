package com.ngapps.googlesigninmvi.core.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ngapps.googlesigninmvi.core.common.result.CustomError
import com.ngapps.googlesigninmvi.core.common.result.DataResult
import com.ngapps.googlesigninmvi.core.datastore.AuthDataSource
import com.ngapps.googlesigninmvi.core.model.AuthDto
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val googleSignIn: GoogleSignInClient,
) : AuthRepository {

    override suspend fun getSignOut() {
        authDataSource.deleteToken()
        googleSignIn.signOut()
    }


    override suspend fun saveAuth(auth: AuthDto): DataResult<AuthDto> {
        return if (auth.token.isNotEmpty()) {
            val authResult = authDataSource.saveAuth(auth)
            if (authResult.token.isNotEmpty()) {
                DataResult.Success(authResult)
            } else {
                DataResult.Error(CustomError.AUTHENTICATION_ERROR)
            }
        } else {
            DataResult.Error(CustomError.AUTHENTICATION_ERROR)
        }
    }

    override fun getAuth(): AuthDto = authDataSource.getAuth()
}

