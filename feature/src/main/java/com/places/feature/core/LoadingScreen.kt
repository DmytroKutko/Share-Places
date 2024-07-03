package com.places.feature.core

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        PostPlaceholderCard()

        Spacer(modifier = Modifier.padding(24.dp))

        PostPlaceholderCard()
    }
}

@Composable
fun PostPlaceholderCard() {
    Column(
        modifier = Modifier
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ComponentAvatar()
            Column(
                modifier = Modifier.padding(start = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ComponentTitle()
                Spacer(modifier = Modifier.padding(4.dp))
                ComponentTitle()
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentImage()
        Spacer(modifier = Modifier.padding(8.dp))
        ComponentTitle()
        Spacer(modifier = Modifier.padding(4.dp))
        ComponentRectangleLineShort()
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    LoadingScreen(modifier = Modifier.fillMaxSize())
}


@Composable
fun ComponentAvatar() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(34.dp))
            .background(color = Color.LightGray)
            .size(68.dp)
            .shimmerLoadingAnimation() // <--- Here.
    )
}

@Composable
fun ComponentImage() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .background(color = Color.LightGray)
            .height(200.dp)
            .fillMaxWidth()
            .shimmerLoadingAnimation() // <--- Here.
    )
}

@Composable
fun ComponentTitle() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .size(height = 30.dp, width = 200.dp)
            .shimmerLoadingAnimation() // <--- Here.
    )
}

@Composable
fun ComponentRectangleLineShort() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .height(height = 96.dp)
            .shimmerLoadingAnimation() // <--- Here.
    )
}

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.shimmerLoadingAnimation(): Modifier {
    return composed {

        val shimmerColors = listOf(
            Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 1.0f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 0.3f),
        )

        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = 100f, y = 0.0f),
                end = Offset(x = 400f, y = 270f),
            ),
        )
    }
}