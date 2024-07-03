package com.places.domain.delegates

import com.places.domain.models.LocationData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressDelegate @Inject constructor() {
    private val _dataFlow = MutableSharedFlow<LocationData>()
    val dataFlow: SharedFlow<LocationData> = _dataFlow

    suspend fun emitData(data: LocationData) {
        _dataFlow.emit(data)
    }
}