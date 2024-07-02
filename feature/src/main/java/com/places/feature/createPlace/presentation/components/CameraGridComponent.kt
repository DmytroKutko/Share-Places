package com.places.feature.createPlace.presentation.components

import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CameraGridComponent(
    isExpanded: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    if (isExpanded) {
        AndroidView(
            factory = {
                PreviewView(it).apply {
                    this.controller = LifecycleCameraController(context).apply {
                        bindToLifecycle(lifecycleOwner)
                        setEnabledUseCases(
                            CameraController.IMAGE_CAPTURE
                        )
                    }
                }
            },
            modifier = modifier
                .fillMaxSize()
        )
    }
}