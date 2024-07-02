package com.share.places.feature.camera.presentation

import android.content.res.Configuration
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.share.places.feature.camera.presentation.components.PhotoDialogComponent

@Composable
fun CameraScreen(
    navController: NavController,
    viewModel: CameraViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val photo by viewModel.photo.collectAsStateWithLifecycle()

    val inPortraitOrientation = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    var showDialog by remember { mutableStateOf(false) }

    val controller: LifecycleCameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
            .fillMaxSize()
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = if (inPortraitOrientation) Alignment.BottomCenter else Alignment.CenterEnd
    ) {
        IconButton(onClick = {
            viewModel.takePhoto(
                controller = controller,
                onPhotoTaken = {
                    showDialog = true
                },
                onPhotoError = {}
            )
        }) {
            Icon(
                modifier = Modifier
                    .size(48.dp),
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = null,
                tint = Color.White
            )
        }
    }

    if (showDialog)
        photo?.let {
            PhotoDialogComponent(
                it,
                onRevert = {
                    showDialog = false
                },
                onCheck = {
                    viewModel.selectPhoto()
                    navController.navigateUp()
                }
            )
        }
}