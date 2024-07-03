package com.places.feature.places.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.places.domain.delegates.place.PlaceUseCases
import com.places.domain.delegates.place.model.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placeUseCases: PlaceUseCases
) : ViewModel() {
    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places = _places.asStateFlow()

    init {
        viewModelScope.launch {
            placeUseCases.getAllPlaces()
                .onStart {

                }
                .catch {

                }
                .collect { data ->
                    _places.emit(data)
                }
        }
    }
}