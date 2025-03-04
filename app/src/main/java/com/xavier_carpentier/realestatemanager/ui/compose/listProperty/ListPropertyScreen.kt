package com.xavier_carpentier.realestatemanager.ui.compose.listProperty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.xavier_carpentier.realestatemanager.R
import com.xavier_carpentier.realestatemanager.ui.compose.utils.LoadingScreen
import com.xavier_carpentier.realestatemanager.ui.listProperty.ListPropertyUIState
import com.xavier_carpentier.realestatemanager.ui.listProperty.ListPropertyViewModel
import com.xavier_carpentier.realestatemanager.ui.model.CurrencyUi
import com.xavier_carpentier.realestatemanager.ui.model.PictureUi
import com.xavier_carpentier.realestatemanager.ui.model.PropertyUi
import com.xavier_carpentier.realestatemanager.ui.model.PropertyWithPictureUi
import com.xavier_carpentier.realestatemanager.ui.theme.AppTheme
import java.util.Calendar

@Composable
fun ListPropertyScreen(viewModel: ListPropertyViewModel = hiltViewModel(),onDetailPressButton: () -> Unit){
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is ListPropertyUIState.Loading -> LoadingScreen()
        is ListPropertyUIState.Empty -> EmptyScreen()
        is ListPropertyUIState.Success -> {
            val state = uiState as ListPropertyUIState.Success
            ListPropertyContent(
                properties = state.listPropertiesWithPicture.orEmpty(),
                currencyUi = state.currency,
                onClick = { property ->
                    viewModel.onSelectProperty(property.propertyUi.id)
                    onDetailPressButton()
                }
            )
        }
    }
}

@Composable
fun EmptyScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Aucune propriété disponible")
    }
}

@Composable
fun ListPropertyContent(
    properties: List<PropertyWithPictureUi>,
    currencyUi : CurrencyUi,
    onClick: (PropertyWithPictureUi) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(properties) { property ->
            ListPropertyItem(
                item = property,
                currentUi = currencyUi,
                onClick = onClick
            )
        }
    }
}

@Preview
@Composable
fun ListPropertyContentPreview() {
    val context = LocalContext.current
    val fakeImage = loadImageAsByteArray(context, R.drawable.image_property_picture)

    val propertyUi1 = PropertyUi(
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
    val picturesUi1 = listOf(
        PictureUi(id = 1, propertyId = 1, description = "Living Room", image = fakeImage)
    )
    val item1 = PropertyWithPictureUi(propertyUi = propertyUi1, picturesUi = picturesUi1)

    val currentUi = CurrencyUi(displayName = "Dollar", symbol = "$")

    val propertyUi2 = PropertyUi(
        id = 2,
        type = "house",
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
    val picturesUi2 = listOf(
        PictureUi(id = 3, propertyId = 2, description = "Living Room", image = fakeImage)
    )
    val item2 = PropertyWithPictureUi(propertyUi = propertyUi2, picturesUi = picturesUi2)

    AppTheme {
        ListPropertyContent(
            listOf(item1, item2, item2, item2, item2, item2),
            currentUi,
            onClick = {})
    }
}


@Preview
@Composable
fun EmptyScreenPreview() {
    AppTheme {
        EmptyScreen()
    }
}