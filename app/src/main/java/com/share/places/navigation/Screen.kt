package com.share.places.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material.icons.outlined.Map
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    data object MapScreen : Screen("map_screen")

    data object PlacesScreen : Screen("places_screen")

    data object CreatePlaceScreen : Screen("create_place_screen")

    data object SelectPlaceScreen : Screen("select_place_screen/{latitude}/{longitude}"){
        fun createRoute(latitude: String?, longitude: String?) =
            "select_place_screen/$latitude/$longitude"
    }
}

sealed class Tab(
    val route: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val label: String,
) {
    data object Places : Tab(
        route = "places_tab",
        icon = BottomAppBarIcons.placesIconOutlined,
        selectedIcon = BottomAppBarIcons.placesIconFilled,
        label = "Home"
    )

    data object Map : Tab(
        route = "map_tab",
        icon = BottomAppBarIcons.mapIconOutlined,
        selectedIcon = BottomAppBarIcons.mapIconFilled,
        label = "Map"
    )

}

private object BottomAppBarIcons {
    val placesIconOutlined = Icons.Outlined.FormatListNumbered
    val placesIconFilled = Icons.Filled.FormatListNumbered

    val mapIconOutlined = Icons.Outlined.Map
    val mapIconFilled = Icons.Filled.Map
}

val bottomNavTabs = listOf(
    Tab.Places,
    Tab.Map,
)