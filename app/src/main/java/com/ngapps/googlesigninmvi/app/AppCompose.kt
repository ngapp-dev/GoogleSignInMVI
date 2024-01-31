package com.ngapps.googlesigninmvi.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.ngapps.googlesigninmvi.app.navigation.AppNavHost
import com.ngapps.googlesigninmvi.core.designsystem.AppBackground

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun AppCompose() {

    AppBackground {
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { padding ->

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
            ) {
                AppNavHost(
                    onShowSnackbar = { message, action ->
                        snackbarHostState.showSnackbar(
                            message = message,
                            actionLabel = action,
                            duration = SnackbarDuration.Short,
                        ) == SnackbarResult.ActionPerformed
                    },
                )
                // TODO: We may want to add padding or spacer when the snackbar is shown so that
                //  content doesn't display behind it.
            }
        }
    }
}