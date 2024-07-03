package com.places.feature.createPlace.models

import android.graphics.Bitmap
import android.net.Uri
import com.google.android.gms.maps.model.LatLng

data class CreatePlaceState(
    val title: String?,
    val description: String?,
    val image: Bitmap?,
    val galleryList: List<Uri>,
    val locationAddress: String,
    val coordinates: LatLng?,
)
