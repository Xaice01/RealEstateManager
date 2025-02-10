package com.xavier_carpentier.realestatemanager.ui.compose.utils

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xavier_carpentier.realestatemanager.R
import com.xavier_carpentier.realestatemanager.ui.compose.listProperty.loadImageAsByteArray
import com.xavier_carpentier.realestatemanager.ui.model.PictureUi

@Composable
fun PhotoWithDescription(picture: PictureUi) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(150.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        val bitmap = BitmapFactory.decodeByteArray(picture.image, 0, picture.image.size).asImageBitmap()

        Image(
            bitmap = bitmap,
            contentDescription = "Property Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f))
                .padding(8.dp)
        ) {
            Text(
                text = picture.description,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }
    }
}

@Preview
@Composable
fun PhotoWithDescriptionPreview() {
    val context = LocalContext.current
    val fakeImage = loadImageAsByteArray(context, R.drawable.image_property_picture)
    PhotoWithDescription(
        PictureUi(id = 1, propertyId = 1, description = "Lounge", image = fakeImage)
    )
}