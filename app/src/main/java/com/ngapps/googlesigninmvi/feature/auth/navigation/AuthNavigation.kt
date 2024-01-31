package com.ngapps.googlesigninmvi.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ngapps.googlesigninmvi.feature.auth.SignInRoute

const val signInRoute = "sign_in_route"

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) {
    this.navigate(signInRoute, navOptions)
}

fun NavGraphBuilder.signInScreen(
    onSignInSuccess: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(route = signInRoute) {
        SignInRoute(
            onSignInSuccess = onSignInSuccess,
            onShowSnackbar = onShowSnackbar,
        )
    }
}
