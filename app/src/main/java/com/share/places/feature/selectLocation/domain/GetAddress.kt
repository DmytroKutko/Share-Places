package com.share.places.feature.selectLocation.domain

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.StringRes
import com.google.android.gms.maps.model.LatLng
import com.places.network.geo.GeoRepository
import com.share.places.BuildConfig
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
                    ?: "Unknown street"
                val number =
                    result.addressComponents.find { it.types.contains("street_number") }?.longName
                        ?: "Unknown number"
                emit("$street $number")
            }
        } catch (e: Exception) {
            emit("Error: ${e.message}")
        }
    }
}