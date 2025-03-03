package com.xavier_carpentier.realestatemanager.ui.compose.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.xavier_carpentier.realestatemanager.ui.compose.listProperty.EmptyScreen
import com.xavier_carpentier.realestatemanager.ui.compose.listProperty.ListPropertyItem
import com.xavier_carpentier.realestatemanager.ui.compose.utils.LoadingScreen
import com.xavier_carpentier.realestatemanager.ui.listProperty.ListPropertyUIState
import com.xavier_carpentier.realestatemanager.ui.map.MapViewModel
import com.xavier_carpentier.realestatemanager.ui.model.CurrencyUi
import com.xavier_carpentier.realestatemanager.ui.model.PropertyWithPictureUi

@Composable
fun MapScreen(viewModel: MapViewModel = hiltViewModel(), onDetailPressButton: () -> Unit){

    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is ListPropertyUIState.Loading -> LoadingScreen()
        is ListPropertyUIState.Empty -> EmptyScreen()
        is ListPropertyUIState.Success -> {
            val state = uiState as ListPropertyUIState.Success
            MapContent(
                properties = state.listPropertiesWithPicture.orEmpty(),
                state.currency,
                onClick = {property ->
                viewModel.onSelectProperty(property.propertyUi.id)
                onDetailPressButton()
                }
            )

        }
    }

}

@Composable
fun MapContent(
    properties: List<PropertyWithPictureUi>,
    currencyUi : CurrencyUi,
    onClick: (item :PropertyWithPictureUi) -> Unit
){

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(48.8584, 2.2945), 5f)
    }

    var isMapLoaded by remember { mutableStateOf(false) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        onMapLoaded = { isMapLoaded = true },
        cameraPositionState = cameraPositionState

    ){
        for (item in properties) {

            MarkerInfoWindowContent(
                state = rememberMarkerState(
                    position = LatLng(
                        item.propertyUi.latitude,
                        item.propertyUi.longitude
                    )
                ),
                onInfoWindowClick = {onClick(item)},
                content = {
                    ListPropertyItem(
                        item = item,
                        currentUi = currencyUi,
                        onClick = {})
                }
            )

        }
    }



    if (!isMapLoaded) {
        AnimatedVisibility(
            visible = !isMapLoaded,
            enter = EnterTransition.None,
            exit = fadeOut()
        ) {
            LoadingScreen()
        }
    }

}