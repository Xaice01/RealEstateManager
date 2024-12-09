package com.xavier_carpentier.realestatemanager.ui.compose.listProperty

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xavier_carpentier.realestatemanager.R
import com.xavier_carpentier.realestatemanager.ui.model.CurrencyUi
import com.xavier_carpentier.realestatemanager.ui.model.PictureUi
import com.xavier_carpentier.realestatemanager.ui.model.PropertyUi
import com.xavier_carpentier.realestatemanager.ui.model.PropertyWithPictureUi
import com.xavier_carpentier.realestatemanager.ui.theme.AppTheme
import java.util.Calendar

@Composable
fun ListPropertyItem(item: PropertyWithPictureUi, currentUi :CurrencyUi, onClick: () -> Unit){
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        onClick = onClick
    ){
        val image = item.picturesUi.first().image
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Item Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 50.dp, max = 100.dp)
                    .height(100.dp)
                    .clip(ShapeDefaults.Medium),
                contentScale = ContentScale.None
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column ( modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                horizontalAlignment = Alignment.Start)
            {
                Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = item.propertyUi.type, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Text(text = item.propertyUi.price.toString()+" "+ currentUi.symbol, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary)
                }
                Spacer(modifier=Modifier.height(4.dp))
                IconAndText(R.drawable.baseline_location_pin_24,"Location",item.propertyUi.address)
                Spacer(modifier=Modifier.height(4.dp))
                Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    IconAndText(R.drawable.outline_dashboard_24,"Rooms",item.propertyUi.room.toString())
                    IconAndText(R.drawable.baseline_bedroom_24,"Bedrooms",item.propertyUi.bedroom.toString())
                    IconAndText(R.drawable.baseline_surface_24,"Surface","${item.propertyUi.surface} m²")
                }
            }
        }

    }
}

@Composable
fun IconAndText(icon: Int,description: String , text: String){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painterResource(icon), contentDescription = description)
        Spacer(modifier= Modifier.width(4.dp))
        Text(text = text)
    }
}

@Composable
fun loadImageAsByteArray(context: Context, resId: Int): ByteArray {
    val inputStream = context.resources.openRawResource(resId)
    return inputStream.readBytes()
}

@Preview
@Composable
fun ListPropertyItemPreview() {
    // Données simulées
    val context = LocalContext.current
    val fakeImage = loadImageAsByteArray(context, R.drawable.image_property_picture)

    val propertyUi = PropertyUi(
        id = 1,
        type = "Apartment",
        price = 300_000L,
        address = "123 Main Street NewYork",
        latitude = 40.7128,
        longitude = -74.0060,
        surface = 120,
        room = 4,
        bedroom = 2,
        description = "A beautiful apartment in the city center.",
        entryDate = Calendar.getInstance(),
        soldDate = null,
        sold = false,
        agent = "John Doe",
        interestNearbySchool = true,
        interestNearbyShop = true,
        interestNearbyPark = true,
        interestNearbyRestaurant = true,
        interestNearbyPublicTransportation = true,
        interestNearbyPharmacy = true
    )
    val picturesUi = listOf(
        PictureUi(id = 1, propertyId = 1, description = "Living Room", image = fakeImage)
    )
    val item = PropertyWithPictureUi(propertyUi = propertyUi, picturesUi = picturesUi)

    val currentUi = CurrencyUi(displayName = "Dollar", symbol = "$")

    AppTheme {
        ListPropertyItem(item = item, currentUi= currentUi, onClick = {})
    }
}