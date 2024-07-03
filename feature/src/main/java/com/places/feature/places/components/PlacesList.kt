package com.places.feature.places.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.places.domain.delegates.place.model.Place

@Composable
fun PlacesList(
    places: List<Place>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.padding(8.dp))
        }

        items(places) { place ->
            PlaceComponent(
                place = place
            )
        }
    }
}