package com.xavier_carpentier.realestatemanager.ui.model

data class PictureUi(
    val id :Int,
    val propertyId :Int,
    val description : String,
    val image: ByteArray
)
