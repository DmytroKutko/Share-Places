package com.places.network.firebase

import com.places.network.firebase.model.FirebasePlace

interface PlaceRepository {
    fun addNewPlace(place: FirebasePlace)

    fun getAllPlaces(): List<FirebasePlace>
}

class PlaceRepositoryImpl(

): PlaceRepository {
    override fun addNewPlace(place: FirebasePlace) {
        TODO("Not yet implemented")
    }

    override fun getAllPlaces(): List<FirebasePlace> {
        TODO("Not yet implemented")
    }

}