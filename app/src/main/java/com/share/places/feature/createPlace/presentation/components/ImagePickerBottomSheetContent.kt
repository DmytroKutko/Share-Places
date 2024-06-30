package com.share.places.feature.createPlace.presentation.components

import android.content.ContentResolver
import android.content.ContentUris
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun ImagePickerBottomSheetContent(
    isExpanded: Boolean,
//    bitmaps: List<Bitmap>,
    cameraClickListener: () -> Unit,
    imageClickListener: (Bitmap) -> Unit,
) {

    val context = LocalContext.current
    val contentResolver: ContentResolver = context.contentResolver
    // Function to fetch images from MediaStore
    val bitmaps = remember { mutableListOf<Bitmap>() }

    LaunchedEffect(Unit) {
        bitmaps.clear()
        bitmaps.addAll(loadImagesFromMediaStore(contentResolver))
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp,
    ) {
        // Camera item
        item {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                CameraGridComponent(
                    isExpanded = isExpanded
                )

                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Camera",
                    tint = Color.White
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            cameraClickListener()
                        }
                )
            }
        }

        items(bitmaps) { bitmap ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        imageClickListener(bitmap)
                    },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = bitmap,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.FillHeight
                )
            }
        }
    }
}


private suspend fun loadImagesFromMediaStore(contentResolver: ContentResolver): List<Bitmap> {
    val images = mutableListOf<Bitmap>()
    withContext(Dispatchers.IO) {
        // Columns you want to retrieve from MediaStore
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN
        )

        // Sort order: descending by date taken
        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

        // Query the MediaStore for images
        val query = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, contentUri)
                images.add(bitmap)
            }
        }
    }
    return images
}