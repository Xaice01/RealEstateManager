package com.xavier_carpentier.realestatemanager.ui.compose.detail

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.xavier_carpentier.realestatemanager.R
import com.xavier_carpentier.realestatemanager.ui.compose.listProperty.loadImageAsByteArray
import com.xavier_carpentier.realestatemanager.ui.compose.utils.DetailSection
import com.xavier_carpentier.realestatemanager.ui.compose.utils.ErrorScreen
import com.xavier_carpentier.realestatemanager.ui.compose.utils.IconAndTextWithTitle
import com.xavier_carpentier.realestatemanager.ui.compose.utils.LoadingScreen
import com.xavier_carpentier.realestatemanager.ui.compose.utils.PhotoWithDescription
import com.xavier_carpentier.realestatemanager.ui.compose.utils.ScreenType
import com.xavier_carpentier.realestatemanager.ui.compose.utils.getScreenType
import com.xavier_carpentier.realestatemanager.ui.detail.DetailViewModel
import com.xavier_carpentier.realestatemanager.ui.detail.PropertyWithPictureUIState
import com.xavier_carpentier.realestatemanager.ui.model.PictureUi
import com.xavier_carpentier.realestatemanager.ui.model.PropertyUi
import com.xavier_carpentier.realestatemanager.ui.model.PropertyWithPictureUi
import com.xavier_carpentier.realestatemanager.ui.theme.AppTheme
import java.util.Calendar


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    val windowSizeClass = (LocalContext.current as? Activity)?.let { calculateWindowSizeClass(it) }
        ?: throw IllegalStateException("Context is not an Activity")
    val screenType = getScreenType(windowSizeClass)


    when (uiState) {
        is PropertyWithPictureUIState.Loading -> LoadingScreen()
        is PropertyWithPictureUIState.Success -> {
            val property = (uiState as PropertyWithPictureUIState.Success).propertyWithPicture
            val mapUrl = viewModel.getMapUrl()

            when (screenType) {
                ScreenType.Compact -> CompactDetailContent(property, mapUrl)
                ScreenType.Medium, ScreenType.Expanded -> ExpandedDetailContent(property, mapUrl)
            }
        }
        is PropertyWithPictureUIState.Empty -> ErrorScreen(message = stringResource(id = R.string.detail_screen_error))
    }

}

@Composable
fun CompactDetailContent(
    property: PropertyWithPictureUi,
    mapUrl: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        DetailSection(
            title = stringResource( R.string.media),
            content = { DetailContentListImage(property.picturesUi) }
        )
        DetailSection(
            title = stringResource(R.string.description),
            content = { Text(text = property.propertyUi.description, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(start = 16.dp, end = 16.dp)) }
        )
        DetailSection(
            title = stringResource(R.string.characteristics),
            content = { CharacteristicsOfProperty(property.propertyUi) }
        )
        DetailSection(
            title = stringResource(R.string.location),
            content = { DetailMapSection(mapUrl = mapUrl) }
        )

    }
}

@Composable
fun ExpandedDetailContent(
    property: PropertyWithPictureUi,
    mapUrl: String
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        DetailSection(
            title = stringResource(R.string.media),
            content = { DetailContentListImage(property.picturesUi) }
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                DetailSection(
                    title = stringResource(R.string.characteristics),
                    content = { CharacteristicsOfProperty(property.propertyUi) }
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                DetailSection(
                    title = stringResource(R.string.description),
                    content = {
                        Text(
                            text = property.propertyUi.description,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                    }
                )
                DetailSection(
                    title = stringResource(R.string.location),
                    content = {
                        DetailMapSection(
                            mapUrl = mapUrl
                        )
                    }
                )
            }
        }
    }
}


@Composable
fun DetailContentListImage(
    listImage: List<PictureUi>,
    modifier: Modifier=Modifier
){
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ){
        items(listImage){picture->
            PhotoWithDescription(picture = picture)
        }
    }
}

@Composable
fun CharacteristicsOfProperty(property: PropertyUi){
    Row (modifier = Modifier.padding(start = 16.dp, end = 16.dp), horizontalArrangement = Arrangement.SpaceBetween){
        Column {
            IconAndTextWithTitle(R.drawable.baseline_surface_24,stringResource(R.string.surface),"${property.surface} m²", stringResource(R.string.surface))
            Spacer(modifier = Modifier.height(8.dp))
            IconAndTextWithTitle(R.drawable.outline_dashboard_24,stringResource(R.string.rooms),property.room.toString(), stringResource(R.string.rooms))
            Spacer(modifier = Modifier.height(8.dp))
            IconAndTextWithTitle(R.drawable.baseline_bedroom_24,stringResource(R.string.bedrooms),property.bedroom.toString(), stringResource(R.string.bedrooms))
        }
        Column {
            IconAndTextWithTitle(R.drawable.baseline_location_pin_24,stringResource(R.string.address),property.address, stringResource(R.string.address))
        }
    }
    Spacer(modifier = Modifier.height(8.dp))

    DetailSection(title = stringResource(R.string.interest),
        content = {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .height(75.dp),
                verticalArrangement = Arrangement.Top,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    listOf(
                        "School" to property.interestNearbySchool,
                        "Shop" to property.interestNearbyShop,
                        "Park" to property.interestNearbyPark,
                        "Restaurant" to property.interestNearbyRestaurant,
                        "Public Transport" to property.interestNearbyPublicTransportation,
                        "Pharmacy" to property.interestNearbyPharmacy
                    )
                ) { (interest, interestNearby) ->
                    InterestSection(interest = interest, interestNearby = interestNearby)
                }
            }
        }
    ,modifier = Modifier.heightIn(min = 50.dp,max = 200.dp)
    )
}

@Composable
fun InterestSection(
    interest: String,
    interestNearby: Boolean
) {
    val icon =if (interestNearby) Icons.Default.Check to Color(0xFF4CAF50) else Icons.Default.Close to Color(0xFFF44336)
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon.first, contentDescription = interest, tint = icon.second)
        Spacer(modifier= Modifier.width(4.dp))
        Text(text = interest)
    }
}

@Composable
fun DetailMapSection(mapUrl: String){
    Row(modifier = Modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        StaticMapView(
            mapUrl = mapUrl,
            modifier = Modifier
                .weight(1f)
                .height(250.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))

    }
}

@Preview
@Composable
fun DetailContentListImagePreview() {
    val context = LocalContext.current
    val fakeImage = loadImageAsByteArray(context, R.drawable.image_property_picture)
    val fakeList = listOf(
           PictureUi(id = 1, propertyId = 1, description = "Lounge 1", image = fakeImage),
           PictureUi(id = 2, propertyId = 1, description = "Lounge 2", image = fakeImage),
           PictureUi(id = 3, propertyId = 1, description = "Lounge 3", image = fakeImage)
       )
    DetailContentListImage(listImage = fakeList)
}

@Preview
@Composable
fun DetailSectionPreview() {
    DetailSection(
        title = "Media",
        content = { DetailContentListImagePreview() }
    )
}

@Preview
@Composable
fun DetailSectionDescriptionPreview() {
    DetailSection(
        title = "Description",
        content = { Text(text="zeifjo  redq jferduhi  rreqdgjeqrdhflv qe glqdghvqer g e lg ueqrhd g eruhrhedfghjqer uir pgu hviuer eg euip gqius gi perdf gi phlers pqçre^ghgip gu^q edg uie qsgpl qui qrieug qeuih wg qeh  ipgqeig plq eeigolv q lg qirup gq er grvqdglqerhglerf ^gqe   ", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(start = 16.dp, end = 16.dp)) }
    )
}

@Preview
@Composable
fun CharacteristicsOfPropertyPreview() {
    val propertyUi = PropertyUi(
        id = 1,
        type = "Apartment",
        price = 300_000L,
        address = "790 Park Avenue apt 6/7A New York NY 10021 United States",
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
        interestNearbyPark = false,
        interestNearbyRestaurant = true,
        interestNearbyPublicTransportation = false,
        interestNearbyPharmacy = true
    )
    DetailSection( title = "Characteristics", content = { CharacteristicsOfProperty(property = propertyUi) })

}

@Preview
@Composable
fun InterestSectionPreview() {
    InterestSection(interest = "School", interestNearby = true)
}


@Preview(widthDp = 400, heightDp = 600)
@Composable
fun CompactDetailPreview() {
    val context = LocalContext.current
    val fakeImage = loadImageAsByteArray(context, R.drawable.image_property_picture)
    val fakeList = listOf(
        PictureUi(id = 1, propertyId = 1, description = "Lounge 1", image = fakeImage),
        PictureUi(id = 2, propertyId = 1, description = "Lounge 2", image = fakeImage),
        PictureUi(id = 3, propertyId = 1, description = "Lounge 3", image = fakeImage)
    )
    val propertyUi = PropertyUi(
        id = 1,
        type = "Apartment",
        price = 300_000L,
        address = "790 Park Avenue apt 6/7A New York NY 10021 United States",
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
        interestNearbyPark = false,
        interestNearbyRestaurant = true,
        interestNearbyPublicTransportation = false,
        interestNearbyPharmacy = true
    )

    val propertyWithPictureUi = PropertyWithPictureUi(propertyUi, fakeList)
    AppTheme {
        CompactDetailContent(propertyWithPictureUi, "")
    }
}

@Preview(widthDp = 800, heightDp = 600)
@Composable
fun ExpandedDetailPreview() {
    val context = LocalContext.current
    val fakeImage = loadImageAsByteArray(context, R.drawable.image_property_picture)
    val fakeList = listOf(
        PictureUi(id = 1, propertyId = 1, description = "Lounge 1", image = fakeImage),
        PictureUi(id = 2, propertyId = 1, description = "Lounge 2", image = fakeImage),
        PictureUi(id = 3, propertyId = 1, description = "Lounge 3", image = fakeImage),
        PictureUi(id = 4, propertyId = 1, description = "Lounge 4", image = fakeImage),
        PictureUi(id = 5, propertyId = 1, description = "Lounge 5", image = fakeImage)
    )

    val propertyUi = PropertyUi(
        id = 1,
        type = "Apartment",
        price = 300_000L,
        address = "790 Park Avenue apt 6/7A New York NY 10021 United States",
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
        interestNearbyPark = false,
        interestNearbyRestaurant = true,
        interestNearbyPublicTransportation = false,
        interestNearbyPharmacy = true
    )
    val propertyWithPictureUi = PropertyWithPictureUi(propertyUi, fakeList)
    AppTheme {
        ExpandedDetailContent(propertyWithPictureUi, mapUrl = "")
    }
}

@Preview
@Composable
fun DetailMapAndButtonSectionPreview() {
    AppTheme {
        DetailMapSection(mapUrl = "")
    }
}