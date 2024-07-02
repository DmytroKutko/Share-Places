package com.places.feature.createPlace.presentation

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.places.feature.createPlace.presentation.models.CreatePlaceState
import com.share.places.feature.core.delegates.AddressDelegate
import com.share.places.feature.core.delegates.CameraDelegate
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
) : ViewModel() {

    private val _placeData =
        MutableStateFlow(CreatePlaceState(null,null,null, emptyList(), "Select Location", null))
    val placeData = _placeData.asStateFlow()

    private val _bitmaps = MutableStateFlow<List<Bitmap>>(emptyList())
    val bitmaps: StateFlow<List<Bitmap>> = _bitmaps.asStateFlow()

    init {
        viewModelScope.launch {
            addressDelegate.dataFlow.collect { data ->
                _placeData.update {
                    it.copy(
                        locationAddress = data.address,
                        coordinates = data.coordinates
                    )
                }
            }
        }

        viewModelScope.launch {
            cameraDelegate.dataFlow.collect { image ->
                _placeData.update {
                    it.copy(
                        selectedImage = image
                    )
                }
            }
        }
    }

    fun setImageFromGallery(bitmap: Bitmap) {
        _placeData.update {
            it.copy(
                selectedImage = bitmap
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
}