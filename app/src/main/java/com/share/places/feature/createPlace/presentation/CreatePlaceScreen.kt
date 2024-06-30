package com.share.places.feature.createPlace.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.LatLng
import com.share.places.feature.createPlace.presentation.components.ImagePickerBottomSheetContent
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
    val locationData by viewModel.locationData.collectAsStateWithLifecycle()

    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    var title by remember { mutableStateOf(TextFieldValue()) }
    var description by remember { mutableStateOf(TextFieldValue()) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler(enabled = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
        scope.launch {
            scaffoldState.bottomSheetState.partialExpand()
        }
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
                emptyList(),
                cameraClickListener = {
                    cameraClickListener()
                    scope.launch {
                        scaffoldState.bottomSheetState.partialExpand()
                    }
                },
                imageClickListener = {

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
            if (locationData.selectedImage != null) {
                AsyncImage(
                    model = locationData.selectedImage,
                    contentDescription = null,
                    modifier = Modifier
                        .widthIn(96.dp, 200.dp)
                        .heightIn(96.dp, 200.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
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
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        locationClickListener(
                            viewModel.locationData.value.coordinates
                        )
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(imageVector = Icons.Default.AddLocation, contentDescription = null)

                Text(
                    text = locationData.locationAddress
                )
            }

            OutlinedTextField(
                maxLines = 1,
                value = title,
                onValueChange = {
                    title = it
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
                value = description,
                onValueChange = {
                    description = it
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