package com.xavier_carpentier.realestatemanager.data.property_type

import androidx.annotation.StringRes
import com.xavier_carpentier.realestatemanager.R
import com.xavier_carpentier.realestatemanager.domain.property_type.PropertyTypeRepository
import com.xavier_carpentier.realestatemanager.domain.property_type.model.PropertyTypeDomain
import javax.inject.Inject

class PropertyTypeRepositoryImpl @Inject constructor(private val propertyTypeMapper: PropertyTypeMapper) : PropertyTypeRepository {
    private val propertyTypes: List<PropertyType> = listOf(
        PropertyType.HOUSE,
        PropertyType.DUPLEX,
        PropertyType.PENTHOUSE,
        PropertyType.FLAT,
        PropertyType.MANOR,
        PropertyType.VILLA
    )

    override fun getPropertyTypes(): List<PropertyTypeDomain> = propertyTypeMapper.mapListToDomain(propertyTypes)
}

enum class PropertyType(
    val id: Long,
    val databaseName: String,
    @StringRes val stringRes: Int
) {
    HOUSE(0, "House", R.string.house),
    DUPLEX(1, "Duplex", R.string.duplex),
    PENTHOUSE(2, "Penthouse", R.string.penthouse),
    FLAT(3, "Flat", R.string.flat),
    MANOR(4, "Manor", R.string.manor),
    VILLA(5, "Villa", R.string.villa)
}