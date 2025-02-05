package com.xavier_carpentier.realestatemanager.data.property_type

import com.xavier_carpentier.realestatemanager.domain.property_type.model.PropertyTypeDomain
import javax.inject.Inject

class PropertyTypeMapper @Inject constructor() {
    fun mapToDomain(propertyType: PropertyType): PropertyTypeDomain {
        return PropertyTypeDomain(
            id = propertyType.id,
            databaseName = propertyType.databaseName,
            stringRes = propertyType.stringRes
        )
    }

    fun mapListToDomain(propertyTypeList: List<PropertyType>): List<PropertyTypeDomain> {
        return propertyTypeList.map { mapToDomain(it) }
    }

    fun mapToData(propertyTypeDomain: PropertyTypeDomain): PropertyType? {
        return PropertyType.entries
            .find { it.id == propertyTypeDomain.id && it.databaseName == propertyTypeDomain.databaseName && it.stringRes == propertyTypeDomain.stringRes }
    }

    fun mapListToData(propertyTypeDomainList: List<PropertyTypeDomain>): List<PropertyType?> {
        return propertyTypeDomainList.map { mapToData(it) }
    }


}