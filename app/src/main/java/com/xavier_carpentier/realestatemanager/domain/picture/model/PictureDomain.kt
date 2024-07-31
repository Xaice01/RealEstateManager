package com.xavier_carpentier.realestatemanager.domain.picture.model

data class PictureDomain(
    val id :Int=0,
    val propertyId :Int,
    val description : String,
    val image: ByteArray)
