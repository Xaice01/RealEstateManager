package com.xavier_carpentier.realestatemanager.ui.model

data class FilterUi(
    val types: List<PropertyTypeUi>?,
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

enum class FilterType {
    MIN_PRICE,
    MAX_PRICE,
    MIN_SURFACE,
    MAX_SURFACE,
    SOLD,
    NEARBY_SCHOOL,
    NEARBY_SHOP,
    NEARBY_PARK,
    NEARBY_RESTAURANT,
    NEARBY_PUBLIC_TRANSPORTATION,
    NEARBY_PHARMACY
}
