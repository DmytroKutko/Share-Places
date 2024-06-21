package com.share.places.feature.createPlace.presentation

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.share.places.feature.createPlace.data.CreatePlaceData
import com.share.places.feature.createPlace.data.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePlaceViewModel @Inject constructor(
    private val database: FirebaseDatabase,
) : ViewModel() {

    private val _locationData = MutableStateFlow(CreatePlaceData(null, emptyList(), "Select Location"))
    val locationData = _locationData.asStateFlow()

    // Save Place to Firebase
    fun savePlace(place: Place) = viewModelScope.launch {
        val myRef = database.getReference("places").push()
        myRef.setValue(place).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Successfully saved
                Log.d("Firebase", "Place saved successfully")
            } else {
                // Failed to save
                Log.e("Firebase", "Failed to save place", task.exception)
            }
        }
    }

    fun fetchImages(context: Context) = viewModelScope.launch{
        val images = mutableListOf<Uri>()
        val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val cursor = context.contentResolver.query(
            uriExternal,
            projection,
            null,
            null,
            "${MediaStore.Images.Media.DATE_ADDED} DESC"
        )

        cursor?.use {
            val columnIndexID = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (it.moveToNext()) {
                val imageId = it.getLong(columnIndexID)
                val uriImage = ContentUris.withAppendedId(uriExternal, imageId)
                images.add(uriImage)
            }
        }
        _locationData.update {
            it.copy(galleryList = images)
        }
    }
}