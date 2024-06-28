package com.share.places.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.android.gms.maps.model.LatLng
import com.share.places.feature.createPlace.presentation.CreatePlaceScreen
import com.share.places.navigation.Screen

fun NavGraphBuilder.createPlace(navController: NavController) {
    composable(
        route = Screen.CreatePlaceScreen.route,
        arguments = listOf(navArgument("address") { type = NavType.StringType }),
    ) { backStackEntry ->
        val address = backStackEntry.arguments?.getString("address") ?: "Select address"
        val latitude = backStackEntry.arguments?.getString("latitude")?.toDoubleOrNull() ?: 37.7749
        val longitude = backStackEntry.arguments?.getString("longitude")?.toDoubleOrNull() ?: -122.4194
        CreatePlaceScreen(
            navController = navController,
            address = address,
            coordinates = LatLng(latitude, longitude),
            selectLocationClicked = { coordinates ->
                navController.navigate(Screen.SelectPlaceScreen.createRoute(
                    coordinates?.latitude.toString(), coordinates?.longitude.toString()
                ))
            }
        )
    }
}