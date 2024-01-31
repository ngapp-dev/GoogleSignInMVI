package com.ngapps.googlesigninmvi.feature.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ngapps.googlesigninmvi.core.domain.GetGoogleSignInUseCase
import com.ngapps.googlesigninmvi.core.domain.GetAuthUseCase
import com.ngapps.googlesigninmvi.core.model.AuthDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val getGoogleSignInUseCase: GetGoogleSignInUseCase,
    googleSignIn: GoogleSignInClient,
    getAuth: GetAuthUseCase,
) : ViewModel() {

    val googleSignInClient = mutableStateOf(googleSignIn)

    private val _signInUiState = MutableStateFlow<SignInUiState>(SignInUiState.Error)
    val signInUiState: StateFlow<SignInUiState> = _signInUiState

    private val _viewEvents = MutableSharedFlow<SignInViewEvent>()
    val viewEvents: SharedFlow<SignInViewEvent> = _viewEvents

    init {
        viewModelScope.launch {
            if (getAuth().token.isNotEmpty()) {
                _signInUiState.value = SignInUiState.Success
            } else {
                _signInUiState.value = SignInUiState.Error
            }
        }
    }

    fun doGoogleSignIn(auth: AuthDto) {
        viewModelScope.launch {
            getGoogleSignInUseCase(auth).checkResult(
                onSuccess = { _signInUiState.value = SignInUiState.Success },
                onError = { _viewEvents.emit(SignInViewEvent.Message(it.name)) },
            )
        }
    }
}

sealed interface SignInUiState {
    data object Success : SignInUiState
    data object Error : SignInUiState
}

sealed class SignInViewEvent {
    data class Message(val message: String) : SignInViewEvent()
}
