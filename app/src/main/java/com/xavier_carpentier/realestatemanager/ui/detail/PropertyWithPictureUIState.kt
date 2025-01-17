package com.xavier_carpentier.realestatemanager.ui.detail

import com.xavier_carpentier.realestatemanager.ui.model.PropertyWithPictureUi

sealed interface PropertyWithPictureUIState {
    data object Empty:PropertyWithPictureUIState
    data object Loading:PropertyWithPictureUIState
    data class Success(val propertyWithPicture :PropertyWithPictureUi) :PropertyWithPictureUIState

}