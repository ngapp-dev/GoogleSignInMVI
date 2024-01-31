package com.ngapps.googlesigninmvi.core.data.repository

import com.ngapps.googlesigninmvi.core.common.result.DataResult
import com.ngapps.googlesigninmvi.core.model.AuthDto

interface AuthRepository {

    suspend fun saveAuth(auth: AuthDto): DataResult<AuthDto>

    fun getAuth(): AuthDto

    suspend fun getSignOut()
}

