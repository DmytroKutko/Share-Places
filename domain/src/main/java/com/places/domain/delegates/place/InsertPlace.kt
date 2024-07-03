package com.places.domain.delegates.place

import com.places.domain.delegates.place.model.Place
import com.places.domain.delegates.place.model.toFirebaseModel
import com.places.network.firebase.PlaceRepository
import javax.inject.Inject

class InsertPlace @Inject constructor(
    private val placeRepository: PlaceRepository
) {
    suspend operator fun invoke(place: Place) {
        placeRepository.insertPlace(place.toFirebaseModel())
    }
}