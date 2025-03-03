package com.xavier_carpentier.realestatemanager.domain.filter.model

import com.xavier_carpentier.realestatemanager.domain.property_type.model.PropertyTypeDomain

data class FilterDomain(
    val types: List<PropertyTypeDomain>?,
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
