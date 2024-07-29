package com.xavier_carpentier.realestatemanager.data.property

import com.xavier_carpentier.realestatemanager.datasource.property.Property
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyDomain

object PropertyMapper {


        fun mapToDomain(property: Property): PropertyDomain {
            return PropertyDomain(
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

        fun mapListToDomain(properties: List<Property>): List<PropertyDomain> {
            return properties.map {
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

        fun mapToData(propertyDomain: PropertyDomain): Property {
            return Property(
                propertyDomain.id,
                propertyDomain.type,
                propertyDomain.price,
                propertyDomain.address,
                propertyDomain.latitude,
                propertyDomain.longitude,
                propertyDomain.surface,
                propertyDomain.room,
                propertyDomain.bedroom,
                propertyDomain.description,
                propertyDomain.entryDate,
                propertyDomain.soldDate,
                propertyDomain.sold,
                propertyDomain.agent,
                propertyDomain.interestNearbySchool,
                propertyDomain.interestNearbyShop,
                propertyDomain.interestNearbyPark,
                propertyDomain.interestNearbyRestaurant,
                propertyDomain.interestNearbyPublicTransportation,
                propertyDomain.interestNearbyPharmacy
            )
        }

        fun mapListToData(propertiesDomain: List<PropertyDomain>): List<Property> {
            return propertiesDomain.map {
                Property(
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