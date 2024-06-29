package com.share.places.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.share.places.feature.camera.presentation.CameraScreen
import com.share.places.navigation.Screen

fun NavGraphBuilder.camera(navController: NavController) {
    composable(
        route = Screen.CameraScreen.route,
    ) {
        CameraScreen(
            navController = navController,
        )
    }
}