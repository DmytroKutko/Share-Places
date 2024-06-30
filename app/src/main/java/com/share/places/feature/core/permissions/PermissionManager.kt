package com.share.places.feature.core.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.share.places.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionManager @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
) {

    fun requestCameraPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            CAMERAX_PERMISSIONS,
            0
        )
    }

    fun requestReadStoragePermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            STORAGE_PERMISSIONS,
            0
        )
    }

    fun hasCameraPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }

    }

    fun hasReadStoragePermissions(): Boolean {
        return STORAGE_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}

private val CAMERAX_PERMISSIONS = arrayOf(
    Manifest.permission.CAMERA,
)

private val STORAGE_PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    arrayOf(
        Manifest.permission.READ_MEDIA_IMAGES,
    )
} else {
    arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )
}