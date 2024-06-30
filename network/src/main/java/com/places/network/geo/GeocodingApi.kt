package com.places.network.geo

import com.places.network.geo.data.GeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApi {
    @GET("geocode/json")
    suspend fun getAddress(
        @Query("latlng") latlng: String,
        @Query("key") apiKey: String
    ): GeocodingResponse
}