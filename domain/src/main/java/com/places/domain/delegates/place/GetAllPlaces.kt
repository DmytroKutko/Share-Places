package com.places.domain.delegates.place

import com.places.domain.delegates.place.model.Place
import com.places.domain.delegates.place.model.toPlaceModel
import com.places.network.firebase.PlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllPlaces @Inject constructor(
    private val placeRepository: PlaceRepository
) {
    suspend operator fun invoke(): Flow<List<Place>> = flow {
        val places = placeRepository.getAllPlaces().map { it.toPlaceModel() }
        emit(places)
    }
}