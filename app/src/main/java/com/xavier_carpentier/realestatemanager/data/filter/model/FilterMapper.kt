package com.xavier_carpentier.realestatemanager.data.filter.model

import com.xavier_carpentier.realestatemanager.data.property_type.PropertyTypeMapper
import com.xavier_carpentier.realestatemanager.domain.filter.model.FilterDomain
import javax.inject.Inject

class FilterMapper @Inject constructor() {
    fun mapToDomain(filter: Filter): FilterDomain{
        return FilterDomain(
            filter.types?.let { PropertyTypeMapper().mapListToDomain(it) },
            filter.minPrice,
            filter.maxPrice,
            filter.minSurface,
            filter.maxSurface,
            filter.sold,
            filter.nearbySchool,
            filter.nearbyShop,
            filter.nearbyPark,
            filter.nearbyRestaurant,
            filter.nearbyPublicTransportation,
            filter.nearbyPharmacy
        )
    }

    fun mapToData(filterDomain: FilterDomain): Filter {
        return Filter(
            filterDomain.types?.let { PropertyTypeMapper().mapListToData(it) },
            filterDomain.minPrice,
            filterDomain.maxPrice,
            filterDomain.minSurface,
            filterDomain.maxSurface,
            filterDomain.sold,
            filterDomain.nearbySchool,
            filterDomain.nearbyShop,
            filterDomain.nearbyPark,
            filterDomain.nearbyRestaurant,
            filterDomain.nearbyPublicTransportation,
            filterDomain.nearbyPharmacy
        )
    }

}