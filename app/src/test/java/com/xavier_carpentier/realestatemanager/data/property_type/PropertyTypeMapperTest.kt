package com.xavier_carpentier.realestatemanager.data.property_type

import com.xavier_carpentier.realestatemanager.domain.property_type.model.PropertyTypeDomain
import org.junit.Assert.assertEquals
import org.junit.Test

class PropertyTypeMapperTest {

    private val mapper = PropertyTypeMapper()

    @Test
    fun `mapToDomain correctly maps PropertyType to PropertyTypeDomain`() {
        // Given: a PropertyType enum value (HOUSE)
        val propertyType = PropertyType.HOUSE

        // When: mapping it to domain using mapToDomain
        val domain: PropertyTypeDomain = mapper.mapToDomain(propertyType)

        // Then: the resulting PropertyTypeDomain should have the same id, databaseName, and stringRes as the input enum
        assertEquals(propertyType.id, domain.id)
        assertEquals(propertyType.databaseName, domain.databaseName)
        assertEquals(propertyType.stringRes, domain.stringRes)
    }

    @Test
    fun `mapListToDomain correctly maps list of PropertyType to list of PropertyTypeDomain`() {
        // Given: a list of PropertyType enum values
        val propertyTypes = listOf(PropertyType.HOUSE, PropertyType.DUPLEX, PropertyType.PENTHOUSE)

        // When: mapping the list to domain using mapListToDomain
        val domainList: List<PropertyTypeDomain> = mapper.mapListToDomain(propertyTypes)

        // Then: the resulting list should have the same size and matching fields for each element
        assertEquals(propertyTypes.size, domainList.size)
        propertyTypes.zip(domainList).forEach { (type, domain) ->
            assertEquals(type.id, domain.id)
            assertEquals(type.databaseName, domain.databaseName)
            assertEquals(type.stringRes, domain.stringRes)
        }
    }

    @Test
    fun `mapToData correctly maps PropertyTypeDomain to PropertyType`() {
        // Given: a PropertyTypeDomain obtained from mapping DUPLEX
        val domain: PropertyTypeDomain = mapper.mapToDomain(PropertyType.DUPLEX)

        // When: mapping it back to data using mapToData
        val data: PropertyType = mapper.mapToData(domain)

        // Then: the returned PropertyType should be equal to DUPLEX
        assertEquals(PropertyType.DUPLEX, data)
    }

    @Test
    fun `mapListToData correctly maps list of PropertyTypeDomain to list of PropertyType`() {
        // Given: a list of PropertyTypeDomain objects for PENTHOUSE and FLAT
        val domains: List<PropertyTypeDomain> = mapper.mapListToDomain(listOf(PropertyType.PENTHOUSE, PropertyType.FLAT))

        // When: mapping the list back to data using mapListToData
        val dataList: List<PropertyType> = mapper.mapListToData(domains)

        // Then: the resulting list should contain PENTHOUSE and FLAT respectively
        assertEquals(2, dataList.size)
        assertEquals(PropertyType.PENTHOUSE, dataList[0])
        assertEquals(PropertyType.FLAT, dataList[1])
    }
}