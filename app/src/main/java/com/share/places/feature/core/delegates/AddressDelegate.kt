package com.share.places.feature.core.delegates

import com.share.places.feature.selectLocation.presentation.models.LocationState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressDelegate @Inject constructor() {
    private val _dataFlow = MutableSharedFlow<LocationState>()
    val dataFlow: SharedFlow<LocationState> = _dataFlow

    suspend fun emitData(data: LocationState) {
        _dataFlow.emit(data)
    }
}