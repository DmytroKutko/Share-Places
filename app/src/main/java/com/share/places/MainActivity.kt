package com.share.places

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.places.mylibrary.AnalyticsService
import com.share.places.core.ui.theme.SharePlacesTheme
import com.share.places.feature.core.permissions.PermissionManager
import com.share.places.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var analyticsService: AnalyticsService

    @Inject
    lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        checkPermissions()
        analyticsService.trackOpenApp()

        setContent {
            SharePlacesTheme {
                AppNavigation()
            }
        }
    }

    private fun checkPermissions() {
        if (!permissionManager.hasLocationPermissions()) {
            permissionManager.requestLocationPermissions(this)
        }

        if (!permissionManager.hasCameraPermissions()) {
            permissionManager.requestCameraPermissions(this)
        }

        if (!permissionManager.hasReadStoragePermissions()) {
            permissionManager.requestReadStoragePermissions(this)
        }
    }
}
