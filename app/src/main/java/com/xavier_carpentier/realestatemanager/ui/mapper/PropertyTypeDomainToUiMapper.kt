package com.xavier_carpentier.realestatemanager.ui.mapper

import com.xavier_carpentier.realestatemanager.domain.property_type.model.PropertyTypeDomain
import com.xavier_carpentier.realestatemanager.ui.model.PropertyTypeUi

class PropertyTypeDomainToUiMapper {

    fun mapToUi(domain: PropertyTypeDomain): PropertyTypeUi {
        return Triple(domain.id.toInt() , domain.databaseName , domain.stringRes)
    }

    fun mapListToUi(domainList: List<PropertyTypeDomain>): List<PropertyTypeUi> {
        return domainList.map { mapToUi(it) }
    }

    fun mapToDomain(ui: PropertyTypeUi): PropertyTypeDomain {
        return PropertyTypeDomain(id = ui.first.toLong(), databaseName = ui.second, stringRes = ui.third)
    }

    fun mapListToDomain(uiList: List<PropertyTypeUi>): List<PropertyTypeDomain> {
        return uiList.map { mapToDomain(it) }
    }
}