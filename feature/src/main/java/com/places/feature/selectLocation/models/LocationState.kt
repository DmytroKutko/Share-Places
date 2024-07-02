package com.places.feature.selectLocation.models

import com.google.android.gms.maps.model.LatLng

data class LocationState(
    val address: String,
    val coordinates: LatLng
)
