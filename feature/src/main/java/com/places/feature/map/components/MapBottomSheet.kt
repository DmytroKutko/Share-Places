package com.places.feature.map.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.places.domain.delegates.place.model.Place

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapBottomSheet(
    place: MutableState<Place?>,
    state: SheetState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        modifier = modifier
            .fillMaxWidth(),
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = state,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            place.value?.image?.let { bitmap ->
                Image(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                fontWeight = FontWeight.Bold,
                text = place.value?.title.toString(),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(text = place.value?.description.toString())
        }
    }
}