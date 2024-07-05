package com.places.feature.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState
import com.places.domain.delegates.place.model.Place
import com.places.feature.map.components.MapBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val places by viewModel.places.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val isSheetOpen = rememberSaveable { mutableStateOf(false) }
    val selectedPlace = remember { mutableStateOf<Place?>(null) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Map")
            })
        },
    ) { paddingValues ->
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
                        onClick = {
                            selectedPlace.value = place
                            isSheetOpen.value = true
                            scope.launch {
                                sheetState.expand()
                            }
                            false
                        }
                    )
                }
            }

            if (isSheetOpen.value) {
                MapBottomSheet(
                    place = selectedPlace,
                    state = sheetState,
                    onDismissRequest = {
                        isSheetOpen.value = false
                        scope.launch {
                            sheetState.hide()
                        }
                    }
                )
            }
        }
    }
}