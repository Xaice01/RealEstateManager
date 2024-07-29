package com.xavier_carpentier.realestatemanager.domain.property.model

import java.util.Calendar

data class PropertyDomain(
    val id: Int,
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
    val soldDate: Calendar?,
    val sold: Boolean,
    val agent: String,
    val interestNearbySchool: Boolean,
    val interestNearbyShop: Boolean,
    val interestNearbyPark: Boolean,
    val interestNearbyRestaurant: Boolean,
    val interestNearbyPublicTransportation: Boolean,
    val interestNearbyPharmacy: Boolean)
