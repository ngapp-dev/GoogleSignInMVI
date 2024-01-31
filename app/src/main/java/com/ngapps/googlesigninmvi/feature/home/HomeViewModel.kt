package com.ngapps.googlesigninmvi.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngapps.googlesigninmvi.core.common.result.Result
import com.ngapps.googlesigninmvi.core.common.result.asResult
import com.ngapps.googlesigninmvi.core.domain.GetAuthUseCase
import com.ngapps.googlesigninmvi.core.model.AuthDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAuth: GetAuthUseCase,
) : ViewModel() {

    val homeState: StateFlow<HomeUiState> = homeUiState(
        getAuth = getAuth
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Error,
        )

    private fun homeUiState(
        getAuth: GetAuthUseCase
    ): Flow<HomeUiState> {
        return flowOf(getAuth.invoke()).asResult()
            .map { authResult ->
                when (authResult) {
                    is Result.Success -> {
                        HomeUiState.Success(authResult.data)
                    }

                    else -> HomeUiState.Error
                }
            }
    }
}

sealed interface HomeUiState {
    data class Success(val auth: AuthDto) : HomeUiState
    data object Error : HomeUiState
}

