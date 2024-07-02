package com.share.places.feature.selectLocation.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EditLocation
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.share.places.feature.selectLocation.presentation.models.LocationState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseLocationScreen(
    navController: NavController,
    coordinates: LatLng,
    viewModel: ChooseLocationViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {

    val locationState: LocationState by viewModel.locationState.collectAsStateWithLifecycle()
    val markerPosition = coordinates

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerPosition, 15f)
    }

    SideEffect {
        if (!cameraPositionState.isMoving) {
            viewModel.updatePosition(
                cameraPositionState.position.target.latitude,
                cameraPositionState.position.target.longitude
            )
        } else {
            viewModel.cameraIsMoving()
        }
    }

    GoogleMap(
        modifier = modifier
            .fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings().copy(zoomControlsEnabled = false)
    )

    Box {
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp)),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color.White,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                    )
                }
            },
            title = {},
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )
    }

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 48.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp)),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.White,
                ),
            ) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = null,
                )
            }

            FloatingActionButton(
                modifier = Modifier
                    .widthIn(min = 80.dp, max = 300.dp),
                containerColor = Color.White,
                onClick = {
                    viewModel.setAddress(locationState.address, locationState.coordinates)
                    navController.navigateUp()
                }
            ) {
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = "Select: ${locationState.address}"
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (cameraPositionState.isMoving) Icons.Filled.EditLocation else Icons.Filled.LocationOn,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .then(
                    if (cameraPositionState.isMoving)
                        Modifier.padding(bottom = 8.dp)
                    else
                        Modifier
                )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
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
                    Street: ${locationState.address}
                """.trimIndent(),
                modifier = Modifier
                    .padding(8.dp)

            )
        }
        Spacer(modifier = Modifier.size(200.dp))
    }

}