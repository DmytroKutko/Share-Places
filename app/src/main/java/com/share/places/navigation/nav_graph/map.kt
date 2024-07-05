package com.share.places.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.places.feature.map.MapScreen
import com.share.places.navigation.Screen
import com.share.places.navigation.Tab

fun NavGraphBuilder.map(navController: NavController) {
    navigation(
        startDestination = Screen.MapScreen.route,
        route = Tab.Map.route
    ) {
        composable(
            route = Screen.MapScreen.route
        ) {
            MapScreen()
        }
    }
}