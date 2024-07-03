package com.places.feature.places.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.PlaceContent(
    bitmap: Bitmap,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Image(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(24.dp))
                .height(200.dp)
                .fillMaxWidth(),
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(text = title)

        Spacer(modifier = Modifier.padding(4.dp))

        Text(text = description)
    }

}