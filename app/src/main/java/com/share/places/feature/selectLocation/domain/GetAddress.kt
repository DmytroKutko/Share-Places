package com.share.places.feature.selectLocation.domain

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.StringRes
import com.google.android.gms.maps.model.LatLng
import com.share.places.api.geo_api.GeocodingApi
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@SuppressLint("SupportAnnotationUsage")
class GetAddress @Inject constructor(
    private val api: GeocodingApi,
    @StringRes private val apiKey: String
) {
    private var latestAddress = ""
    suspend operator fun invoke(coordinated: LatLng) = flow {
        try {
            val response = api.getAddress("${coordinated.latitude},${coordinated.longitude}", apiKey)
            Log.d("geo_debug", "invoke: $response")
            if (response.status == "OK" && response.results.isNotEmpty()) {
                val result = response.results[0]
                val street = result.addressComponents.find { it.types.contains("route") }?.longName
                    ?: "Unknown street"
                val number =
                    result.addressComponents.find { it.types.contains("street_number") }?.longName
                        ?: "Unknown number"
                latestAddress = "$street $number"
                emit("$street $number")
            } else {
                emit(latestAddress)
            }
        } catch (e: Exception) {
            emit("Error: ${e.message}")
        }
    }
}