package com.xavier_carpentier.realestatemanager.ui.mapper

import com.xavier_carpentier.realestatemanager.domain.filter.model.FilterDomain
import com.xavier_carpentier.realestatemanager.ui.model.FilterUi
import com.xavier_carpentier.realestatemanager.ui.model.PropertyTypeUi
import junit.framework.TestCase.assertEquals
import org.junit.Test

class FilterDomainToUiMapperTest {

    @Test
    fun `mapToUi should correctly map FilterDomain to FilterUi`() {
        // Given: a FilterDomain instance with sample data (including types)
        val propertyTypeDomain = com.xavier_carpentier.realestatemanager.domain.property_type.model.PropertyTypeDomain(1, "House", 100)
        val filterDomain = FilterDomain(
            types = listOf(propertyTypeDomain),
            minPrice = 100000,
            maxPrice = 500000,
            minSurface = 50,
            maxSurface = 200,
            sold = false,
            nearbySchool = true,
            nearbyShop = false,
            nearbyPark = true,
            nearbyRestaurant = false,
            nearbyPublicTransportation = true,
            nearbyPharmacy = false
        )
        // When: mapping to UI model
        val filterUi: FilterUi = FilterDomainToUiMapper.mapToUi(filterDomain)
        // Then: each field should be correctly mapped (including types via PropertyTypeDomainToUiMapper)
        val expectedType: PropertyTypeUi = Triple(1, "House", 100)
        assertEquals(listOf(expectedType), filterUi.types)
        assertEquals(100000, filterUi.minPrice)
        assertEquals(500000, filterUi.maxPrice)
        assertEquals(50, filterUi.minSurface)
        assertEquals(200, filterUi.maxSurface)
        assertEquals(false, filterUi.sold)
        assertEquals(true, filterUi.nearbySchool)
        assertEquals(false, filterUi.nearbyShop)
        assertEquals(true, filterUi.nearbyPark)
        assertEquals(false, filterUi.nearbyRestaurant)
        assertEquals(true, filterUi.nearbyPublicTransportation)
        assertEquals(false, filterUi.nearbyPharmacy)
    }

    @Test
    fun `mapToDomain should correctly map FilterUi to FilterDomain`() {
        // Given: a FilterUi instance with sample data (including types)
        val propertyTypeUi: PropertyTypeUi = Triple(1, "House", 100)
        val filterUi = FilterUi(
            types = listOf(propertyTypeUi),
            minPrice = 100000,
            maxPrice = 500000,
            minSurface = 50,
            maxSurface = 200,
            sold = false,
            nearbySchool = true,
            nearbyShop = false,
            nearbyPark = true,
            nearbyRestaurant = false,
            nearbyPublicTransportation = true,
            nearbyPharmacy = false
        )
        // When: mapping to domain model
        val filterDomain: FilterDomain = FilterDomainToUiMapper.mapToDomain(filterUi)
        // Then: each field should be correctly mapped (including types via PropertyTypeDomainToUiMapper)
        val expectedType = com.xavier_carpentier.realestatemanager.domain.property_type.model.PropertyTypeDomain(1, "House", 100)
        assertEquals(listOf(expectedType), filterDomain.types)
        assertEquals(100000, filterDomain.minPrice)
        assertEquals(500000, filterDomain.maxPrice)
        assertEquals(50, filterDomain.minSurface)
        assertEquals(200, filterDomain.maxSurface)
        assertEquals(false, filterDomain.sold)
        assertEquals(true, filterDomain.nearbySchool)
        assertEquals(false, filterDomain.nearbyShop)
        assertEquals(true, filterDomain.nearbyPark)
        assertEquals(false, filterDomain.nearbyRestaurant)
        assertEquals(true, filterDomain.nearbyPublicTransportation)
        assertEquals(false, filterDomain.nearbyPharmacy)
    }
}