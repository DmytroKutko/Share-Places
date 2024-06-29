package com.share.places.feature.createPlace.presentation.components

import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CameraGridComponent(modifier: Modifier = Modifier) {
//    val context = LocalContext.current
//    val controller: LifecycleCameraController = remember {
//        LifecycleCameraController(context).apply {
//            setEnabledUseCases(
//                CameraController.IMAGE_CAPTURE
//            )
//        }
//    }
//    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = {
            PreviewView(it).apply {
//                this.controller = controller
//                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
    )
}