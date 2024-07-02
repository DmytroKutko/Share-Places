package com.share.places.feature.core.delegates

import android.graphics.Bitmap
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CameraDelegate @Inject constructor() {
    private val _dataFlow = MutableSharedFlow<Bitmap>()
    val dataFlow: SharedFlow<Bitmap> = _dataFlow

    suspend fun emitData(data: Bitmap) {
        _dataFlow.emit(data)
    }
}