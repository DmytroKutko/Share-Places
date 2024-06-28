package com.share.places.feature.createPlace.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.share.places.feature.createPlace.data.CreatePlaceData
import com.share.places.feature.selectLocation.domain.CreatePlaceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePlaceViewModel @Inject constructor(
) : ViewModel() {

    private val _locationData =
        MutableStateFlow(CreatePlaceData(null, emptyList(), "Select Location", null))
    val locationData = _locationData.asStateFlow()

    fun setAddress(address: String, coordinates: LatLng) = viewModelScope.launch{
        _locationData.update {
            it.copy(
                locationAddress = address,
                coordinates = coordinates
            )
        }
    }
}