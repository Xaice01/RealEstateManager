package com.xavier_carpentier.realestatemanager.data.property_type

import org.junit.Assert.assertEquals
import org.junit.Test

class PropertyTypeRepositoryImplTest {

    // Given: an instance of PropertyTypeMapper
    private val mapper = PropertyTypeMapper()

    // And: an instance of PropertyTypeRepositoryImpl initialized with the mapper
    private val repository = PropertyTypeRepositoryImpl(mapper)

    @Test
    fun `getPropertyTypes returns mapped list of PropertyTypeDomain`() {
        // Given: the repository holds a fixed list of PropertyType enums:
        // HOUSE, DUPLEX, PENTHOUSE, FLAT, MANOR, VILLA
        // When: calling getPropertyTypes on the repository
        val domainList = repository.getPropertyTypes()

        // Then: the resulting list should match the expected mapped list from the fixed enum values
        val expectedDomains = mapper.mapListToDomain(
            listOf(
                PropertyType.HOUSE,
                PropertyType.DUPLEX,
                PropertyType.PENTHOUSE,
                PropertyType.FLAT,
                PropertyType.MANOR,
                PropertyType.VILLA
            )
        )
        assertEquals(expectedDomains, domainList)
    }
}