package com.places.feature.selectLocation

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.places.domain.delegates.AddressDelegate
import com.places.domain.models.LocationData
import com.places.domain.selectLocation.ChooseLocationUseCases
import com.places.feature.selectLocation.models.LocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseLocationViewModel @Inject constructor(
    private val useCases: ChooseLocationUseCases,
    private val addressDelegate: AddressDelegate
) : ViewModel() {
    private val _locationState = MutableStateFlow(LocationState("", LatLng(0.0, 0.0)))
    val locationState = _locationState.asStateFlow()

    fun updatePosition(latitude: Double, longitude: Double) = viewModelScope.launch {
        useCases.getAddress(LatLng(latitude, longitude))
            .catch {
                _locationState.update {
                    it.copy(
                        address = "Error loading",
                    )
                }
            }
            .collect { address ->
                _locationState.update {
                    it.copy(
                        address = address,
                        coordinates = LatLng(latitude, longitude)
                    )
                }
            }
    }

    fun setAddress(address: String, coordinates: LatLng) = viewModelScope.launch {
        addressDelegate.emitData(
            LocationData(
                address,
                "",
                coordinates.latitude,
                coordinates.longitude
            )
        )
    }

    fun cameraIsMoving() = viewModelScope.launch {
        _locationState.update {
            it.copy(
                address = "Loading..."
            )
        }
    }


    @SuppressLint("MissingPermission")
    fun getUserLocation(context: Context, onLocationFound: (CameraUpdate) -> Unit) {
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { loc ->
                viewModelScope.launch {
                    val yourLocation = LatLng(loc.latitude, loc.longitude)
                    val update = CameraUpdateFactory.newLatLngZoom(yourLocation, 12f)
                    onLocationFound(update)
                }
                Log.d("ChooseLocationScreen_debug", "ChooseLocationScreen: $loc")
            }
    }
}