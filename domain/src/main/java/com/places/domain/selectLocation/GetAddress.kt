package com.places.domain.selectLocation

import android.annotation.SuppressLint
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.places.domain.BuildConfig
import com.places.network.geo.GeoRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@SuppressLint("SupportAnnotationUsage")
class GetAddress @Inject constructor(
    private val repository: GeoRepository
) {
    suspend operator fun invoke(coordinated: LatLng) = flow {
        try {
            val response = repository.getAddress("${coordinated.latitude},${coordinated.longitude}", BuildConfig.GEO_KEY)
            Log.d("geo_debug", "invoke: $response")
            if (response.status == "OK" && response.results.isNotEmpty()) {
                val result = response.results[0]
                val street = result.addressComponents.find { it.types.contains("route") }?.longName
                    ?: "Unknown location"
                val number =
                    result.addressComponents.find { it.types.contains("street_number") }?.longName
                        ?: ""
                emit("$street $number")
            }
        } catch (e: Exception) {
            emit("Error: ${e.message}")
        }
    }
}