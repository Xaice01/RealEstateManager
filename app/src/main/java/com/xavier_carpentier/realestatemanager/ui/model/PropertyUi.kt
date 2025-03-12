package com.xavier_carpentier.realestatemanager.ui.model

import java.util.Calendar

data class PropertyUi(
    val id: Int=0,
    val type: String,
    val price: Long,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val surface: Long,
    val room: Int,
    val bedroom: Int,
    val description: String,
    val entryDate: Calendar,
    var soldDate: Calendar?,
    val sold: Boolean,
    val agent: String,
    val interestNearbySchool: Boolean,
    val interestNearbyShop: Boolean,
    val interestNearbyPark: Boolean,
    val interestNearbyRestaurant: Boolean,
    val interestNearbyPublicTransportation: Boolean,
    val interestNearbyPharmacy: Boolean
)