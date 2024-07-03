package com.places.feature.createPlace

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.LatLng
import com.places.feature.createPlace.components.ImagePickerBottomSheetContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlaceScreen(
    navController: NavController,
    locationClickListener: (LatLng?) -> Unit,
    cameraClickListener: () -> Unit,
    viewModel: CreatePlaceViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val placeData by viewModel.placeData.collectAsStateWithLifecycle()
    val bitmaps by viewModel.bitmaps.collectAsStateWithLifecycle()

    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var cameraPermissionGranted by remember { mutableStateOf(false) }
    var mediaPermissionGranted by remember { mutableStateOf(false) }

    BackHandler(enabled = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
        scope.launch {
            scaffoldState.bottomSheetState.partialExpand()
        }
    }

// Permission launcher for CAMERA
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            cameraPermissionGranted = isGranted
        }
    )

// Permission launcher for READ_MEDIA_IMAGES
    val mediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            mediaPermissionGranted = isGranted
        }
    )

// Check for READ_MEDIA_IMAGES permission and request if not granted
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Manifest.permission.READ_MEDIA_IMAGES
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                }
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            mediaLauncher.launch(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Manifest.permission.READ_MEDIA_IMAGES
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                }
            )
        } else {
            mediaPermissionGranted = true
        }
    }

    LaunchedEffect(mediaPermissionGranted) {
        viewModel.getAllImagesFromGallery(context)
    }

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                            scope.launch {
                                scaffoldState.bottomSheetState.partialExpand()
                            }
                        } else {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(text = "New Place")
                }
            )
        },
        sheetPeekHeight = 0.dp,
        sheetContent = {
            ImagePickerBottomSheetContent(
                isExpanded = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded,
                bitmaps = bitmaps,
                cameraClickListener = {
                    cameraClickListener()
                    scope.launch {
                        scaffoldState.bottomSheetState.partialExpand()
                    }
                },
                imageClickListener = { bitmap ->
                    viewModel.setImageFromGallery(bitmap)
                    scope.launch {
                        scaffoldState.bottomSheetState.partialExpand()
                    }
                }
            )
        },

        ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 12.dp,
                    end = 12.dp
                )
                .clickable(
                    onClick = {
                        scope.launch {
                            scaffoldState.bottomSheetState.partialExpand()
                        }
                    },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.size(24.dp))
            if (placeData.image != null) {
                AsyncImage(
                    model = placeData.image,
                    contentDescription = null,
                    modifier = Modifier
                        .widthIn(96.dp, 200.dp)
                        .heightIn(96.dp, 200.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            if (!cameraPermissionGranted) {
                                viewModel.requestCameraPermission(context, cameraLauncher) {
                                    cameraPermissionGranted = true
                                }
                            }
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }

                        },
                    contentScale = ContentScale.FillHeight
                )
            } else {
                Image(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = null,
                    modifier = Modifier
                        .widthIn(96.dp, 200.dp)
                        .heightIn(96.dp, 200.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            if (!cameraPermissionGranted) {
                                viewModel.requestCameraPermission(context, cameraLauncher) {
                                    cameraPermissionGranted = true
                                }
                            }
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }

                        }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .clickable {
                        locationClickListener(
                            viewModel.placeData.value.coordinates
                        )
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(imageVector = Icons.Default.AddLocation, contentDescription = null)

                Text(
                    text = placeData.locationAddress,
                    fontSize = 24.sp
                )
            }

            OutlinedTextField(
                maxLines = 1,
                value = placeData.title ?: "",
                onValueChange = {
                    viewModel.setTitle(it)
                },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            OutlinedTextField(
                value = placeData.description ?: "",
                onValueChange = {
                    viewModel.setDescription(it)
                },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(96.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            )

            Button(
                modifier = Modifier
                    .width(96.dp),
                onClick = { /*TODO*/ }) {
                Text(text = "Save")
            }
        }
    }
}