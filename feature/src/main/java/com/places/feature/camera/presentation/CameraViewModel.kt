package com.places.feature.camera.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.share.places.feature.core.delegates.CameraDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cameraDelegate: CameraDelegate,
) : ViewModel() {
    private val _photo = MutableStateFlow<Bitmap?>(null)
    val photo = _photo.asStateFlow()

    fun takePhoto(
        controller: LifecycleCameraController,
        onPhotoTaken: () -> Unit,
        onPhotoError: (String?) -> Unit,
    ) {
        controller.takePicture(
            ContextCompat.getMainExecutor(context),
            object : OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)

                    val matrix = Matrix().apply {
                        postRotate(image.imageInfo.rotationDegrees.toFloat())
                    }
                    val rotatedBitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0,
                        0,
                        image.width,
                        image.height,
                        matrix,
                        true
                    )

                    viewModelScope.launch {
                        onPhotoTaken()
                        _photo.emit(rotatedBitmap)
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    onPhotoError(exception.localizedMessage)
                }
            }
        )
    }

    fun selectPhoto() = viewModelScope.launch {
        photo.value?.let {
            cameraDelegate.emitData(it)
        }
    }
}