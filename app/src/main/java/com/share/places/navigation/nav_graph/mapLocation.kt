package com.share.places.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.android.gms.maps.model.LatLng
import com.share.places.feature.selectLocation.presentation.ChooseLocationScreen
import com.share.places.navigation.Screen

fun NavGraphBuilder.mapLocation(navController: NavController) {
    composable(
        route = Screen.MapLocationScreen.route
    ) { backStackEntry ->

        val latitude = backStackEntry.arguments?.getString("latitude")?.toDoubleOrNull() ?: 37.7749
        val longitude = backStackEntry.arguments?.getString("longitude")?.toDoubleOrNull() ?: -122.4194

        ChooseLocationScreen(
            navController,
            coordinates = LatLng(latitude, longitude)
        )
    }
}