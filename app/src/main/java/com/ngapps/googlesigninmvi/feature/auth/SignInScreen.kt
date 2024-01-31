package com.ngapps.googlesigninmvi.feature.auth

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.ngapps.googlesigninmvi.R
import com.ngapps.googlesigninmvi.core.common.GoogleSignInResultContract
import com.ngapps.googlesigninmvi.core.designsystem.AppButton
import com.ngapps.googlesigninmvi.core.designsystem.AppDivider
import com.ngapps.googlesigninmvi.core.designsystem.AppTopAppBar
import com.ngapps.googlesigninmvi.core.model.AuthDto
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
internal fun SignInRoute(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
    onSignInSuccess: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val googleSignInClient by remember { viewModel.googleSignInClient }
    val signInUiState: SignInUiState by viewModel.signInUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.viewEvents.collectLatest { event ->
            when (event) {
                is SignInViewEvent.Message -> onShowSnackbar.invoke(event.message, null)
            }
        }
    }

    when (signInUiState) {
        SignInUiState.Success -> LaunchedEffect(key1 = Unit) { onSignInSuccess() }
        SignInUiState.Error -> {
            SignInScreen(
                googleSignInClient = googleSignInClient,
                modifier = modifier,
                onGoogleSignInCompleted = { viewModel.doGoogleSignIn(it) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@VisibleForTesting
@Composable
internal fun SignInScreen(
    googleSignInClient: GoogleSignInClient,
    onGoogleSignInCompleted: (AuthDto) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(Modifier.fillMaxSize()) {
        AppTopAppBar(
            titleRes = R.string.title_sign_in,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
        )
        SignInContent(
            modifier = modifier,
            enabled = true,
            googleSignInClient = googleSignInClient,
            onGoogleSignInCompleted = onGoogleSignInCompleted,
            onGoogleSignInError = {},
        )
    }
}

@VisibleForTesting
@Composable
internal fun SignInContent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    googleSignInClient: GoogleSignInClient,
    onGoogleSignInCompleted: (AuthDto) -> Unit,
    onGoogleSignInError: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 32.dp),
    ) {
        Text(
            text = "Hello, please sign in",
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(Modifier.height(8.dp))
        AppDivider()
        Spacer(Modifier.height(16.dp))
        ButtonGoogleSignIn(
            enabled = enabled,
            onGoogleSignInCompleted = onGoogleSignInCompleted,
            onError = onGoogleSignInError,
            googleSignInClient = googleSignInClient,
        )
    }
}

@Composable
fun ButtonGoogleSignIn(
    enabled: Boolean,
    onGoogleSignInCompleted: (AuthDto) -> Unit,
    onError: () -> Unit,
    googleSignInClient: GoogleSignInClient,
) {
    val coroutineScope = rememberCoroutineScope()
    val signInRequestCode = 1

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = GoogleSignInResultContract(googleSignInClient)) {
            try {
                val account = it?.getResult(ApiException::class.java)
                if (account == null) {
                    onError()
                } else {
                    coroutineScope.launch {
                        Log.e("token Id", account.idToken ?: "empty")
                        onGoogleSignInCompleted(
                            AuthDto(
                                token = account.idToken ?: "",
                                givenName = account.givenName,
                                familyName = account.familyName,
                                photoUrl = account.photoUrl?.toString()
                            )
                        )
                    }
                }
            } catch (e: ApiException) {
                onError()
            }
        }

    AppButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 54.dp),
        enabled = enabled,
        onClick = { authResultLauncher.launch(signInRequestCode) },
        text = {
            Text(
                text = stringResource(R.string.continue_with_google),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Normal,
                ),
            )
        },
    )
}

