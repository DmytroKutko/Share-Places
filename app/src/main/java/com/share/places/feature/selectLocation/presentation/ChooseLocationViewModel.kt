package com.share.places.feature.selectLocation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.share.places.feature.selectLocation.data.PositionData
import com.share.places.feature.selectLocation.domain.CreatePlaceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseLocationViewModel @Inject constructor(
    private val useCases: CreatePlaceUseCases,
//    private val addressListener: MutableSharedFlow<PositionData?>
) : ViewModel() {
    private val _position = MutableStateFlow(PositionData(null, null))
    val position = _position.asStateFlow()

    fun updatePosition(latitude: Double, longitude: Double) = viewModelScope.launch {
        useCases.getAddress(LatLng(latitude, longitude))
            .catch {
                _position.update {
                    it.copy(
                        address = "Error loading",
                    )
                }
            }
            .collect { address ->
                _position.update {
                    it.copy(
                        address = address,
                        coordinates = LatLng(latitude, longitude)
                    )
                }
            }
    }

//    fun setAddress() = viewModelScope.launch {
//        addressListener.emit(_position.value)
//    }

    fun cameraIsMoving() = viewModelScope.launch {
        _position.update {
            it.copy(
                address = "Loading..."
            )
        }
    }
}