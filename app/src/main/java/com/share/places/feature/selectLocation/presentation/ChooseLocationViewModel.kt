package com.share.places.feature.selectLocation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.share.places.feature.core.delegates.AddressDelegate
import com.share.places.feature.selectLocation.presentation.models.LocationState
import com.share.places.feature.selectLocation.domain.ChooseLocationUseCases
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
        addressDelegate.emitData(LocationState(address, coordinates))
    }

    fun cameraIsMoving() = viewModelScope.launch {
        _locationState.update {
            it.copy(
                address = "Loading..."
            )
        }
    }
}