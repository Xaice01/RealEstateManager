package com.xavier_carpentier.realestatemanager.ui.mapper

import com.xavier_carpentier.realestatemanager.domain.property_type.model.PropertyTypeDomain
import com.xavier_carpentier.realestatemanager.ui.model.PropertyTypeUi
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PropertyTypeDomainToUiMapperTest {

    private val mapper = PropertyTypeDomainToUiMapper()

    @Test
    fun `mapToUi should correctly map PropertyTypeDomain to PropertyTypeUi`() {
        // Given: a PropertyTypeDomain instance
        val domain = PropertyTypeDomain(id = 1, databaseName = "House", stringRes = 100)
        // When: mapping to UI model
        val ui: PropertyTypeUi = mapper.mapToUi(domain)
        // Then: the UI model should be the expected Triple
        val expectedUi: PropertyTypeUi = Triple(1, "House", 100)
        assertEquals(expectedUi, ui)
    }

    @Test
    fun `mapListToUi should correctly map list of PropertyTypeDomain to list of PropertyTypeUi`() {
        // Given: a list of PropertyTypeDomain instances
        val domainList = listOf(
            PropertyTypeDomain(1, "House", 100),
            PropertyTypeDomain(2, "Apartment", 200)
        )
        // When: mapping the list to UI models
        val uiList = mapper.mapListToUi(domainList)
        // Then: the resulting list should match the expected list of Triples
        val expectedList = listOf(Triple(1, "House", 100), Triple(2, "Apartment", 200))
        assertEquals(expectedList, uiList)
    }

    @Test
    fun `mapToDomain should correctly map PropertyTypeUi to PropertyTypeDomain`() {
        // Given: a PropertyTypeUi instance (Triple)
        val ui: PropertyTypeUi = Triple(3, "Villa", 300)
        // When: mapping to domain model
        val domain = mapper.mapToDomain(ui)
        // Then: the domain model should have the expected values
        val expectedDomain = PropertyTypeDomain(id = 3, databaseName = "Villa", stringRes = 300)
        assertEquals(expectedDomain, domain)
    }

    @Test
    fun `mapListToDomain should correctly map list of PropertyTypeUi to list of PropertyTypeDomain`() {
        // Given: a list of PropertyTypeUi instances
        val uiList: List<PropertyTypeUi> = listOf(Triple(3, "Villa", 300), Triple(4, "Studio", 400))
        // When: mapping the list to domain models
        val domainList = mapper.mapListToDomain(uiList)
        // Then: the resulting list should match the expected list of PropertyTypeDomain objects
        val expectedList = listOf(
            PropertyTypeDomain(id = 3, databaseName = "Villa", stringRes = 300),
            PropertyTypeDomain(id = 4, databaseName = "Studio", stringRes = 400)
        )
        assertEquals(expectedList, domainList)
    }
}