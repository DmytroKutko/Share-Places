package com.places.feature.createPlace

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.places.domain.delegates.AddressDelegate
import com.places.domain.delegates.CameraDelegate
import com.places.domain.delegates.place.PlaceUseCases
import com.places.domain.delegates.place.model.Place
import com.places.domain.utils.bitmapToBase64
import com.places.feature.createPlace.models.CreatePlaceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePlaceViewModel @Inject constructor(
    private val addressDelegate: AddressDelegate,
    private val cameraDelegate: CameraDelegate,
    private val placeUseCases: PlaceUseCases,
) : ViewModel() {

    private val _placeData = MutableStateFlow(CreatePlaceState(null, null, null, emptyList(), "Select Location", null))
    val placeData = _placeData.asStateFlow()

    private val _bitmaps = MutableStateFlow<List<Bitmap>>(emptyList())
    val bitmaps: StateFlow<List<Bitmap>> = _bitmaps.asStateFlow()

    init {
        viewModelScope.launch {
            addressDelegate.dataFlow.collect { data ->
                _placeData.update {
                    it.copy(
                        locationAddress = data.address,
                        coordinates = LatLng(data.latitude, data.longitude)
                    )
                }
            }
        }

        viewModelScope.launch {
            cameraDelegate.dataFlow.collect { image ->
                _placeData.update {
                    it.copy(
                        image = image
                    )
                }
            }
        }
    }

    fun setImageFromGallery(bitmap: Bitmap) {
        _placeData.update {
            it.copy(
                image = bitmap
            )
        }
    }

    fun getAllImagesFromGallery(context: Context) = viewModelScope.launch {
        val imageUris = mutableListOf<Uri>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
        )

        val queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor = context.contentResolver.query(
            queryUri,
            projection,
            null,
            null,
            "${MediaStore.Images.Media.DATE_ADDED} DESC"
        )

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val contentUri = Uri.withAppendedPath(queryUri, id.toString())
                imageUris.add(contentUri)
            }
        } ?: run {
            Log.e("getAllImagesFromGallery", "Failed to retrieve images.")
        }

        val bitmapImages = imageUris.map { uri ->
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }

        _bitmaps.emit(bitmapImages)
    }

    fun setTitle(title: String) = viewModelScope.launch {
        _placeData.update {
            it.copy(
                title = title
            )
        }
    }

    fun setDescription(description: String) = viewModelScope.launch {
        _placeData.update {
            it.copy(
                description = description
            )
        }
    }

    fun requestCameraPermission(context: Context, cameraLauncher: ManagedActivityResultLauncher<String, Boolean>, onSuccess: () -> Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            cameraLauncher.launch(Manifest.permission.CAMERA)
        } else {
            onSuccess()
        }
    }

    fun insertPlace() = viewModelScope.launch {
        placeData.value.also { place ->
            placeUseCases.insertPlace(Place(
                title = place.title!!,
                description = place.description!!,
                address = place.locationAddress,
                country = "",
                image = place.image!!,
                latitude = place.coordinates!!.latitude,
                longitude = place.coordinates.longitude
            ))
        }
    }
}