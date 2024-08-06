package com.xavier_carpentier.realestatemanager.ui.listProperty

import com.xavier_carpentier.realestatemanager.ui.model.PropertyWithPictureUi

sealed interface ListPropertyUIState {
    data object Empty:ListPropertyUIState
    data object Loading:ListPropertyUIState
    data class Success(val listPropertiesWithPicture :List<PropertyWithPictureUi>?) :ListPropertyUIState

}