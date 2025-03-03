package com.xavier_carpentier.realestatemanager.data.filter.model

import com.xavier_carpentier.realestatemanager.data.property_type.PropertyType

data class Filter(
    val types: List<PropertyType>?,
    val minPrice: Long,
    val maxPrice: Long,
    val minSurface: Long,
    val maxSurface: Long,
    val sold: Boolean?,
    val nearbySchool: Boolean,
    val nearbyShop: Boolean,
    val nearbyPark: Boolean,
    val nearbyRestaurant: Boolean,
    val nearbyPublicTransportation: Boolean,
    val nearbyPharmacy: Boolean
)
