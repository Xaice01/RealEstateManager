package com.xavier_carpentier.realestatemanager.ui.model

data class PictureUi(
    val id :Int=0,
    val propertyId :Int,
    val description : String,
    val image: ByteArray
)
