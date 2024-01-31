package com.ngapps.googlesigninmvi.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ngapps.googlesigninmvi.feature.home.HomeRoute

private const val homeGraphRoutePattern = "home_graph"
const val homeRoute = "home_route"

fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null) {
    this.navigate(homeGraphRoutePattern, navOptions)
}

fun NavGraphBuilder.homeGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = homeGraphRoutePattern,
        startDestination = homeRoute,
    ) {
        composable(route = homeRoute) {
            HomeRoute()
        }
        nestedGraphs()
    }
}
