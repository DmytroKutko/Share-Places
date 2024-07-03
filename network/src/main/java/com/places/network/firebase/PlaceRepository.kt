package com.places.network.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.places.network.firebase.model.FirebasePlace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface PlaceRepository {
    suspend fun insertPlace(place: FirebasePlace)

    suspend fun getAllPlaces(): List<FirebasePlace>
}

@Singleton
internal class PlaceRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase
) : PlaceRepository {

    private val placesRef: DatabaseReference by lazy {
        database.getReference("places") // "places" is the path in your Firebase Realtime Database
    }

    override suspend fun insertPlace(place: FirebasePlace) {
        val key = placesRef.push().key ?: throw Exception("Push key generation failed")
        placesRef.child(key).setValue(place).await()
    }

    override suspend fun getAllPlaces(): List<FirebasePlace> {
        return withContext(Dispatchers.IO) {
            try {
                val snapshot = placesRef.get().await()
                val places = mutableListOf<FirebasePlace>()
                snapshot.children.forEach { data ->
                    val place = data.getValue(FirebasePlace::class.java)
                    place?.let { places.add(it) }
                }
                places
            } catch (e: Exception) {
                // Handle error appropriately (logging, rethrowing, etc.)
                emptyList()
            }
        }
    }
}