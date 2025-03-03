package com.xavier_carpentier.realestatemanager.ui.mapper

import com.xavier_carpentier.realestatemanager.domain.filter.model.FilterDomain
import com.xavier_carpentier.realestatemanager.ui.model.FilterUi

object FilterDomainToUiMapper {
    fun mapToUi(filterDomain: FilterDomain): FilterUi {
        return FilterUi(
            filterDomain.types?.let { PropertyTypeDomainToUiMapper().mapListToUi(it) },
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

    fun mapToDomain(filterUi :FilterUi): FilterDomain {
        return FilterDomain(
            filterUi.types?.let{ PropertyTypeDomainToUiMapper().mapListToDomain(it) },
            filterUi.minPrice,
            filterUi.maxPrice,
            filterUi.minSurface,
            filterUi.maxSurface,
            filterUi.sold,
            filterUi.nearbySchool,
            filterUi.nearbyShop,
            filterUi.nearbyPark,
            filterUi.nearbyRestaurant,
            filterUi.nearbyPublicTransportation,
            filterUi.nearbyPharmacy
        )
    }
}