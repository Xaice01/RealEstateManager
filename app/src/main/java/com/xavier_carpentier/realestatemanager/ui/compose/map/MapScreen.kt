package com.xavier_carpentier.realestatemanager.ui.compose.map

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.xavier_carpentier.realestatemanager.ui.map.MapViewModel

@Composable
fun MapScreen(viewModel: MapViewModel = hiltViewModel(), onDetailPressButton: () -> Unit){
    //todo Create screen

    Text("Map Screen")
}