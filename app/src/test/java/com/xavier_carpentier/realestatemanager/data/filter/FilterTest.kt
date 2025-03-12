package com.xavier_carpentier.realestatemanager.data.filter

import com.xavier_carpentier.realestatemanager.data.filter.model.Filter
import org.junit.Assert.assertEquals
import org.junit.Test

class FilterTest {

    @Test
    fun `test Filter equality and copy`() {
        // Given
        val filter1 = Filter(
            types = null,
            minPrice = 100000,
            maxPrice = 500000,
            minSurface = 50,
            maxSurface = 200,
            sold = false,
            nearbySchool = true,
            nearbyShop = true,
            nearbyPark = false,
            nearbyRestaurant = false,
            nearbyPublicTransportation = true,
            nearbyPharmacy = false
        )
        val filter2 = Filter(
            types = null,
            minPrice = 100000,
            maxPrice = 500000,
            minSurface = 50,
            maxSurface = 200,
            sold = false,
            nearbySchool = true,
            nearbyShop = true,
            nearbyPark = false,
            nearbyRestaurant = false,
            nearbyPublicTransportation = true,
            nearbyPharmacy = false
        )
        
        // When
        // Then
        assertEquals(filter1, filter2)

        // Given
        val filterCopy = filter1.copy()
        // When
        // Then
        assertEquals(filter1, filterCopy)
    }
}