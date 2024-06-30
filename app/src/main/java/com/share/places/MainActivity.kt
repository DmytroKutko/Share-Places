package com.share.places

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.analytics.FirebaseAnalytics
import com.share.places.feature.core.permissions.PermissionManager
import com.share.places.feature.core.ui.theme.SharePlacesTheme
import com.share.places.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var permissionManager: PermissionManager

    @Inject
    lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (!permissionManager.hasCameraPermissions()) {
            permissionManager.requestCameraPermissions(this)
        }
        if (!permissionManager.hasReadStoragePermissions()) {
            permissionManager.requestReadStoragePermissions(this)
        }

        setContent {
            SharePlacesTheme {
                AppNavigation()
            }
        }
    }
}
