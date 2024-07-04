package com.places.feature.map.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val places by viewModel.places.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Map")
        })
    }) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                uiSettings = MapUiSettings().copy(zoomControlsEnabled = false)
            ) {
                places.forEach { place ->
                    Marker(
                        state = rememberMarkerState(
                            position = LatLng(
                                place.latitude,
                                place.longitude
                            )
                        ),
                        title = place.title,
                    )
                }
            }
        }
    }
}