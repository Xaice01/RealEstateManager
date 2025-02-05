package com.xavier_carpentier.realestatemanager.domain.property_type

import com.xavier_carpentier.realestatemanager.domain.property_type.model.PropertyTypeDomain
import javax.inject.Inject

class GetPropertyTypeUseCase @Inject constructor(private val propertyTypeRepository: PropertyTypeRepository) {
    operator fun invoke(): List<PropertyTypeDomain> {
        return propertyTypeRepository.getPropertyTypes()
    }
}