package com.places.domain.delegates.place.model

import com.places.network.firebase.model.FirebasePlace

data class Place(
    val title: String,
    val description: String,
    val address: String,
    val country: String,
    val image: String,
    val latitude: Double,
    val longitude: Double
)

fun Place.toFirebaseModel() = FirebasePlace(
    title = this.title,
    description = this.description,
    address = this.address,
    country = this.country,
    image = this.image,
    latitude = this.latitude,
    longitude = this.longitude
)

fun FirebasePlace.toPlaceModel() = Place(
    title = this.title,
    description = this.description,
    address = this.address,
    country = this.country,
    image = this.image,
    latitude = this.latitude,
    longitude = this.longitude
)