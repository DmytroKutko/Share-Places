package com.share.places.feature.createPlace.presentation.models

import android.graphics.Bitmap
import android.net.Uri
import com.google.android.gms.maps.model.LatLng

data class CreatePlaceState(
    val title: String?,
    val description: String?,
    val selectedImage: Bitmap?,
    val galleryList: List<Uri>,
    val locationAddress: String,
    val coordinates: LatLng?,
)
