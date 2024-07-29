package com.xavier_carpentier.realestatemanager.domain.picture.model

data class PictureDomain(
    val id :Int,
    val propertyId :Int,
    val description : String,
    val image: ByteArray)
