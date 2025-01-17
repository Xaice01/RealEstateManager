package com.xavier_carpentier.realestatemanager.ui.compose.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun StaticMapView(
    mapUrl: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberAsyncImagePainter(mapUrl),
        contentDescription = "Map static",
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun StaticMapPreview() {
    val zoom: Int = 14
    val width: Int = 250
    val height: Int = 250
    val latitude: Double = 48.8566
    val longitude: Double = 2.3522
    val apiKey: String ="Api_Key"

    val mapUrl = "https://maps.googleapis.com/maps/api/staticmap?" +
            "center=$latitude,$longitude" +
            "&zoom=$zoom" +
            "&size=${width}x$height" +
            "&maptype=roadmap" +
            "&key=$apiKey"

    StaticMapView(
        mapUrl = mapUrl,
        modifier = Modifier
            .height(height.dp)
            .width(width.dp)
    )
}