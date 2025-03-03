package com.xavier_carpentier.realestatemanager.ui.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

typealias PropertyTypeUi = Triple<Int, String, Int>

data class PropertyTypeUiCheckable(val propertyTypeUi: PropertyTypeUi,val initialChecked:Boolean = false){
    var checked: Boolean by mutableStateOf(initialChecked)
}