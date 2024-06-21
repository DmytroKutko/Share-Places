package com.share.places.feature.createPlace.data

import android.graphics.Bitmap
import android.net.Uri

data class CreatePlaceData(
    val selectedImage: Bitmap?,
    val galleryList: List<Uri>,
    val locationAddress: String
)
