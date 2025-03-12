package com.xavier_carpentier.realestatemanager.data.filter

import com.xavier_carpentier.realestatemanager.data.filter.model.Filter
import com.xavier_carpentier.realestatemanager.data.filter.model.FilterMapper
import com.xavier_carpentier.realestatemanager.domain.filter.model.FilterDomain
import org.junit.Assert.assertEquals
import org.junit.Test

class FilterMapperTest {

    private val filterMapper = FilterMapper()

    @Test
    fun `test mapToDomain with null types`() {
        // Given
        val filter = Filter(
            types = null,
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
        // When
        val filterDomain = filterMapper.mapToDomain(filter)
        // Then
        val expected = FilterDomain(
            types = null,
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
        assertEquals(expected, filterDomain)
    }

    @Test
    fun `test mapToData with null types`() {
        // Given
        val filterDomain = FilterDomain(
            types = null,
            minPrice = 150000,
            maxPrice = 600000,
            minSurface = 60,
            maxSurface = 250,
            sold = true,
            nearbySchool = false,
            nearbyShop = true,
            nearbyPark = false,
            nearbyRestaurant = true,
            nearbyPublicTransportation = false,
            nearbyPharmacy = true
        )
        // When
        val filter = filterMapper.mapToData(filterDomain)
        // Then
        val expected = Filter(
            types = null,
            minPrice = 150000,
            maxPrice = 600000,
            minSurface = 60,
            maxSurface = 250,
            sold = true,
            nearbySchool = false,
            nearbyShop = true,
            nearbyPark = false,
            nearbyRestaurant = true,
            nearbyPublicTransportation = false,
            nearbyPharmacy = true
        )
        assertEquals(expected, filter)
    }

    @Test
    fun `test round-trip mapping`() {
        // Given
        val original = FilterDomain(
            types = null,
            minPrice = 200000,
            maxPrice = 800000,
            minSurface = 70,
            maxSurface = 300,
            sold = null,
            nearbySchool = true,
            nearbyShop = true,
            nearbyPark = true,
            nearbyRestaurant = true,
            nearbyPublicTransportation = false,
            nearbyPharmacy = false
        )
        // When
        val filter = filterMapper.mapToData(original)
        val mappedBack = filterMapper.mapToDomain(filter)
        // Then
        assertEquals(original, mappedBack)
    }
}