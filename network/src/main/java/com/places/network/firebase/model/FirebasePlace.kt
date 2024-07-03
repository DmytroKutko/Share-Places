package com.places.network.firebase.model

data class FirebasePlace(
    val title: String,
    val description: String,
    val address: String,
    val country: String,
    val image: String,
    val latitude: Double,
    val longitude: Double
) {
    // Default constructor is required for firebase models
    constructor() : this("", "", "", "", "", 0.0, 0.0)
}
