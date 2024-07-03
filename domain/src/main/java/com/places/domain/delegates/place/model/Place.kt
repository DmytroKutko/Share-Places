package com.places.domain.delegates.place.model

import android.graphics.Bitmap
import com.places.domain.utils.base64ToBitmap
import com.places.domain.utils.bitmapToBase64
import com.places.network.firebase.model.FirebasePlace

data class Place(
    val title: String,
    val description: String,
    val address: String,
    val country: String,
    val image: Bitmap,
    val latitude: Double,
    val longitude: Double
)

fun Place.toFirebaseModel() = FirebasePlace(
    title = this.title,
    description = this.description,
    address = this.address,
    country = this.country,
    image = this.image.bitmapToBase64(),
    latitude = this.latitude,
    longitude = this.longitude
)

fun FirebasePlace.toPlaceModel() = Place(
    title = this.title,
    description = this.description,
    address = this.address,
    country = this.country,
    image = this.image.base64ToBitmap(),
    latitude = this.latitude,
    longitude = this.longitude
)