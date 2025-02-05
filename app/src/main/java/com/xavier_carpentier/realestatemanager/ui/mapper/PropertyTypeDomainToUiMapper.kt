package com.xavier_carpentier.realestatemanager.ui.mapper

import com.xavier_carpentier.realestatemanager.domain.property_type.model.PropertyTypeDomain
import com.xavier_carpentier.realestatemanager.ui.model.PropertyTypeUi

class PropertyTypeDomainToUiMapper {

    fun mapToUi(domain: PropertyTypeDomain): PropertyTypeUi {
        return PropertyTypeUi(id = domain.id, databaseName = domain.databaseName, stringRes = domain.stringRes)
    }

    fun mapListToUi(domainList: List<PropertyTypeDomain>): List<PropertyTypeUi> {
        return domainList.map { mapToUi(it) }
    }

    fun mapToDomain(ui: PropertyTypeUi): PropertyTypeDomain {
        return PropertyTypeDomain(id = ui.id, databaseName = ui.databaseName, stringRes = ui.stringRes)
    }

    fun mapListToDomain(uiList: List<PropertyTypeUi>): List<PropertyTypeDomain> {
        return uiList.map { mapToDomain(it) }
    }
}