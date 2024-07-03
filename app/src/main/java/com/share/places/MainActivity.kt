package com.share.places

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.places.mylibrary.AnalyticsService
import com.share.places.core.ui.theme.SharePlacesTheme
import com.share.places.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var analyticsService: AnalyticsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        analyticsService.trackOpenApp()

        setContent {
            SharePlacesTheme {
                AppNavigation()
            }
        }
    }
}
