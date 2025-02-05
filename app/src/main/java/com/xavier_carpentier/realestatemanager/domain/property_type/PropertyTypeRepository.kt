package com.xavier_carpentier.realestatemanager.domain.property_type

import com.xavier_carpentier.realestatemanager.domain.property_type.model.PropertyTypeDomain

interface PropertyTypeRepository {
    fun getPropertyTypes(): List<PropertyTypeDomain>
}