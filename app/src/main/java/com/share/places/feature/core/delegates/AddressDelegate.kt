package com.share.places.feature.core.delegates

import com.share.places.feature.selectLocation.data.PositionData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressDelegate @Inject constructor() {
    private val _dataFlow = MutableSharedFlow<PositionData>()
    val dataFlow: SharedFlow<PositionData> = _dataFlow

    suspend fun emitData(data: PositionData) {
        _dataFlow.emit(data)
    }
}