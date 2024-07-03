package com.places.feature.places

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.places.domain.delegates.place.model.Place
import com.places.feature.core.LoadingScreen
import com.places.feature.places.components.PlacesList
import com.places.feature.utils.StateUi

@Composable
fun PlacesListScreen(
    addPlaceClickListener: () -> Unit,
    viewModel: PlacesViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val places by viewModel.places.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                addPlaceClickListener()
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (places) {
                is StateUi.Error -> {

                }

                StateUi.Idle -> {

                }

                StateUi.Loading -> {
                    LoadingScreen()
                }

                is StateUi.Success -> {
                    val data = (places as StateUi.Success<List<Place>>).data

                    PlacesList(places = data)
                }
            }

        }
    }
}