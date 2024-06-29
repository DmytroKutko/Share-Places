package com.share.places.feature.camera.presentation

import android.content.Context
import android.graphics.Bitmap
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cameraDelegate: CameraDelegate,
) : ViewModel() {

    fun takePhoto(
        controller: LifecycleCameraController,
        onPhotoTaken: (Bitmap) -> Unit,
        onPhotoError: (String?) -> Unit,
    ) {
        controller.takePicture(
            ContextCompat.getMainExecutor(context),
            object : OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    onPhotoTaken(image.toBitmap())
                    viewModelScope.launch {
                        cameraDelegate.emitData(image.toBitmap())
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    onPhotoError(exception.localizedMessage)
                }
            }
        )
    }
}