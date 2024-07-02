package com.share.places.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.places.feature.createPlace.CreatePlaceScreen
import com.share.places.navigation.Screen

fun NavGraphBuilder.createPlace(navController: NavController) {
    composable(
        route = Screen.CreatePlaceScreen.route,
    ) {
        CreatePlaceScreen(
            navController = navController,
            locationClickListener = { coordinates ->
                navController.navigate(Screen.MapLocationScreen.createRoute(
                    coordinates?.latitude.toString(), coordinates?.longitude.toString()
                ))
            },
            cameraClickListener = {
                navController.navigate(Screen.CameraScreen.route)
            }
        )
    }
}