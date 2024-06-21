package com.share.places.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.share.places.feature.createPlace.presentation.CreatePlaceScreen
import com.share.places.feature.createPlace.presentation.components.ChooseLocationScreen
import com.share.places.navigation.Screen

fun NavGraphBuilder.selectPlace(navController: NavController) {
    composable(
        route = Screen.SelectPlaceScreen.route
    ) {
        ChooseLocationScreen()
    }
}