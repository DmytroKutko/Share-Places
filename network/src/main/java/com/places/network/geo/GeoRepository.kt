package com.places.network.geo

import com.places.network.geo.data.GeocodingResponse
import javax.inject.Inject
import javax.inject.Singleton

interface GeoRepository {
    suspend fun getAddress(latlng: String, apiKey: String): GeocodingResponse
}

@Singleton
class GeoRepositoryImpl @Inject constructor(
    private val api: GeocodingApi
) : GeoRepository {
    override suspend fun getAddress(latlng: String, apiKey: String): GeocodingResponse =
        api.getAddress(latlng, apiKey)
}