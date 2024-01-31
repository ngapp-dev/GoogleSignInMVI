package com.ngapps.googlesigninmvi.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ngapps.googlesigninmvi.feature.auth.navigation.signInRoute
import com.ngapps.googlesigninmvi.feature.auth.navigation.signInScreen
import com.ngapps.googlesigninmvi.feature.home.navigation.homeGraph
import com.ngapps.googlesigninmvi.feature.home.navigation.navigateToHomeGraph

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun AppNavHost(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = signInRoute,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        signInScreen(
            onShowSnackbar = onShowSnackbar,
            onSignInSuccess = {
                navController.popBackStack(startDestination, true)
                navController.navigateToHomeGraph()
            },
        )
        homeGraph(nestedGraphs = {})
    }
}
