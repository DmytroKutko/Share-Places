package com.places.network.geo

import com.places.network.geo.data.GeocodingResponse
import javax.inject.Inject

interface GeoRepository {
    suspend fun getAddress(latlng: String, apiKey: String): GeocodingResponse
}

class GeoRepositoryImpl @Inject constructor(
    private val api: GeocodingApi
) : GeoRepository {
    override suspend fun getAddress(latlng: String, apiKey: String): GeocodingResponse =
        api.getAddress(latlng, apiKey)
}