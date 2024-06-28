package com.share.places.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.share.places.feature.places.presentation.PlacesListScreen
import com.share.places.navigation.Screen
import com.share.places.navigation.Tab

fun NavGraphBuilder.places(navController: NavController) {
    navigation(
        startDestination = Screen.PlacesScreen.route,
        route = Tab.Places.route
    ) {
        composable(
            route = Screen.PlacesScreen.route
        ) {
            PlacesListScreen(
                addPlaceClickListener = {
                    navController.navigate(
                        route = Screen.CreatePlaceScreen.createRoute(null, null, null)
                    )
                }
            )
        }
    }
}