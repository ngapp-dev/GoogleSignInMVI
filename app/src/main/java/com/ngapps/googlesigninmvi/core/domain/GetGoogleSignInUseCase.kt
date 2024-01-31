package com.ngapps.googlesigninmvi.core.domain

import com.ngapps.googlesigninmvi.core.common.result.DataResult
import com.ngapps.googlesigninmvi.core.data.repository.AuthRepository
import com.ngapps.googlesigninmvi.core.model.AuthDto
import javax.inject.Inject

class GetGoogleSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(auth: AuthDto): DataResult<AuthDto> =
        authRepository.saveAuth(auth).checkResultAndReturn(
            onSuccess = {
                authRepository.saveAuth(auth)
                DataResult.Success(auth)
            },
            onError = { DataResult.Error(it) },
        )
}