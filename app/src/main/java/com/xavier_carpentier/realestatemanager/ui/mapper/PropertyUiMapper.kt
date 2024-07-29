package com.xavier_carpentier.realestatemanager.ui.mapper

import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyDomain
import com.xavier_carpentier.realestatemanager.ui.model.PropertyUi

object PropertyUiMapper {

    fun mapToUi(property: PropertyDomain): PropertyUi {
        return PropertyUi(
            property.id,
            property.type,
            property.price,
            property.address,
            property.latitude,
            property.longitude,
            property.surface,
            property.room,
            property.bedroom,
            property.description,
            property.entryDate,
            property.soldDate,
            property.sold,
            property.agent,
            property.interestNearbySchool,
            property.interestNearbyShop,
            property.interestNearbyPark,
            property.interestNearbyRestaurant,
            property.interestNearbyPublicTransportation,
            property.interestNearbyPharmacy
        )
    }

    fun mapListToUi(properties: List<PropertyDomain>): List<PropertyUi> {
        return properties.map {
            PropertyUi(
                it.id,
                it.type,
                it.price,
                it.address,
                it.latitude,
                it.longitude,
                it.surface,
                it.room,
                it.bedroom,
                it.description,
                it.entryDate,
                it.soldDate,
                it.sold,
                it.agent,
                it.interestNearbySchool,
                it.interestNearbyShop,
                it.interestNearbyPark,
                it.interestNearbyRestaurant,
                it.interestNearbyPublicTransportation,
                it.interestNearbyPharmacy
            )
        }
    }

    fun mapToDomain(propertyUi: PropertyUi): PropertyDomain {
        return PropertyDomain(
            propertyUi.id,
            propertyUi.type,
            propertyUi.price,
            propertyUi.address,
            propertyUi.latitude,
            propertyUi.longitude,
            propertyUi.surface,
            propertyUi.room,
            propertyUi.bedroom,
            propertyUi.description,
            propertyUi.entryDate,
            propertyUi.soldDate,
            propertyUi.sold,
            propertyUi.agent,
            propertyUi.interestNearbySchool,
            propertyUi.interestNearbyShop,
            propertyUi.interestNearbyPark,
            propertyUi.interestNearbyRestaurant,
            propertyUi.interestNearbyPublicTransportation,
            propertyUi.interestNearbyPharmacy
        )
    }

    fun mapListToDomain(propertyUiList: List<PropertyUi>): List<PropertyDomain> {
        return propertyUiList.map {
            PropertyDomain(
                it.id,
                it.type,
                it.price,
                it.address,
                it.latitude,
                it.longitude,
                it.surface,
                it.room,
                it.bedroom,
                it.description,
                it.entryDate,
                it.soldDate,
                it.sold,
                it.agent,
                it.interestNearbySchool,
                it.interestNearbyShop,
                it.interestNearbyPark,
                it.interestNearbyRestaurant,
                it.interestNearbyPublicTransportation,
                it.interestNearbyPharmacy
            )
        }
    }
}