package com.places.network.geo.data

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class GeocodingResponse(
    @Json(name = "results") val results: List<Result>,
    @Json(name = "status") val status: String
)

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "address_components") val addressComponents: List<AddressComponent>,
    @Json(name = "formatted_address") val formattedAddress: String,
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "place_id") val placeId: String,
    @Json(name = "types") val types: List<String>
)

@JsonClass(generateAdapter = true)
data class AddressComponent(
    @Json(name = "long_name") val longName: String,
    @Json(name = "short_name") val shortName: String,
    @Json(name = "types") val types: List<String>
)

@JsonClass(generateAdapter = true)
data class Geometry(
    @Json(name = "location") val location: Location
)

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "lat") val lat: Double,
    @Json(name = "lng") val lng: Double
)