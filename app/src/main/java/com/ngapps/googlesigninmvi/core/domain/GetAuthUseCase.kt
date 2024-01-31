package com.ngapps.googlesigninmvi.core.domain

import com.ngapps.googlesigninmvi.core.data.repository.AuthRepository
import com.ngapps.googlesigninmvi.core.model.AuthDto
import javax.inject.Inject

class GetAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(): AuthDto = authRepository.getAuth()
}


