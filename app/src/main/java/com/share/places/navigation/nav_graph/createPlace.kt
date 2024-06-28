package com.share.places.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.share.places.feature.createPlace.presentation.CreatePlaceScreen
import com.share.places.navigation.Screen

fun NavGraphBuilder.createPlace(navController: NavController) {
    composable(
        route = Screen.CreatePlaceScreen.route,
    ) {
        CreatePlaceScreen(
            navController = navController,
            selectLocationClicked = { coordinates ->
                navController.navigate(Screen.SelectPlaceScreen.createRoute(
                    coordinates?.latitude.toString(), coordinates?.longitude.toString()
                ))
            }
        )
    }
}