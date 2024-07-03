package com.places.feature.places.components

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.places.domain.delegates.place.model.Place

@Composable
fun PlaceComponent(
    place: Place,
    onPostClicked:(place: Place) -> Unit,
    title: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {
        PlaceContent(bitmap = place.image, title = place.title, description = place.description)
    },
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onPostClicked(place)
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            title.invoke(this)

            content.invoke(this)
        }
    }
}

@Preview
@Composable
fun PlaceComponentPreview() {
    PlaceComponent(
        place = Place(
            title = "Title",
            description = "Description",
            address = "street 123",
            country = "",
            image = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),
            latitude = 0.0,
            longitude = 0.0
        ),
        onPostClicked = {}
    )
}