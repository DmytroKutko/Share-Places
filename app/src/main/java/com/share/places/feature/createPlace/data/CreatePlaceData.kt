package com.share.places.feature.createPlace.data

import android.graphics.Bitmap
import android.net.Uri
import com.google.android.gms.maps.model.LatLng

data class CreatePlaceData(
    val selectedImage: Bitmap?,
    val galleryList: List<Uri>,
    val locationAddress: String,
    val coordinates: LatLng?,
)
