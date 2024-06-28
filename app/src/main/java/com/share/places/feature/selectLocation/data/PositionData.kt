package com.share.places.feature.selectLocation.data

import com.google.android.gms.maps.model.LatLng

data class PositionData(
    val address: String?,
    val coordinates: LatLng?
)
