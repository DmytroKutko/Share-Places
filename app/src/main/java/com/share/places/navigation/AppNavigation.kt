package com.share.places.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.share.places.navigation.nav_graph.createPlace
import com.share.places.navigation.nav_graph.map
import com.share.places.navigation.nav_graph.places
import com.share.places.navigation.nav_graph.selectPlace

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val isBottomAppBarVisible = rememberSaveable(navBackStackEntry) {
        navBackStackEntry?.destination?.route == Screen.MapScreen.route ||
                navBackStackEntry?.destination?.route == Screen.PlacesScreen.route
    }

    Scaffold(
        bottomBar = {
            if (isBottomAppBarVisible) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()),
            navController = navController,
            startDestination = Tab.Places.route
        ) {
            map(navController)
            places(navController)
            createPlace(navController)
            selectPlace(navController)
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomNavTabs.forEach { tab ->
            val selected = currentDestination?.hierarchy?.any {
                it.route == tab.route
            } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) tab.selectedIcon else tab.icon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = tab.label,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    }
}