package com.places.domain.utils

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

// Convert Bitmap to Base64 String
fun Bitmap.bitmapToBase64(): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}