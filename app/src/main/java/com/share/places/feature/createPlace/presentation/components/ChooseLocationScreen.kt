package com.share.places.feature.createPlace.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.share.places.feature.createPlace.presentation.CreatePlaceViewModel

@Composable
fun ChooseLocationScreen(
    viewModel: CreatePlaceViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val markerPosition = LatLng(37.7749, -122.4194)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerPosition, 10f)
    }

    // Choose location view
    GoogleMap(
        modifier = modifier
            .fillMaxSize(),
        cameraPositionState = cameraPositionState
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .padding(8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = """
                    Is camera moving: ${cameraPositionState.isMoving}
                    Latitude: ${cameraPositionState.position.target.latitude}
                    Longitude: ${cameraPositionState.position.target.longitude}
                    Street: 
                """.trimIndent(),
                modifier = Modifier
                    .padding(8.dp)

            )
        }

        Icon(
            imageVector = Icons.Default.PinDrop,
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
    }
}