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
        PropertyType.VILLA,
        PropertyType.ALL,
        PropertyType.OTHER,
    )

    override fun getPropertyTypes(): List<PropertyTypeDomain> = propertyTypeMapper.mapListToDomain(propertyTypes)
}

enum class PropertyType(
    val id: Long,
    val databaseName: String,
    @StringRes val stringRes: Int
) {
    HOUSE(1L, "House", R.string.house),
    DUPLEX(2L, "Duplex", R.string.duplex),
    PENTHOUSE(3L, "Penthouse", R.string.penthouse),
    FLAT(4L, "Flat", R.string.flat),
    MANOR(5L, "Manor", R.string.manor),
    VILLA(6L, "Villa", R.string.villa),
    OTHER(7L, "Other", R.string.other),
    ALL(0L, "All", R.string.all),
}