package com.xavier_carpentier.realestatemanager.ui.mapper

import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyDomain
import com.xavier_carpentier.realestatemanager.ui.model.PropertyUi
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.Calendar

class PropertyUiMapperTest {

    @Test
    fun `mapToUi should correctly convert PropertyDomain to PropertyUi`() {
        // Given: a PropertyDomain instance with sample data
        val entryDate = Calendar.getInstance()
        val propertyDomain = PropertyDomain(
            id = 1,
            type = "House",
            price = 250000,
            address = "123 Main St",
            latitude = 0.0,
            longitude = 0.0,
            surface = 120,
            room = 5,
            bedroom = 3,
            description = "A nice house",
            entryDate = entryDate,
            soldDate = null,
            sold = false,
            agent = "Agent A",
            interestNearbySchool = true,
            interestNearbyShop = false,
            interestNearbyPark = true,
            interestNearbyRestaurant = false,
            interestNearbyPublicTransportation = true,
            interestNearbyPharmacy = false
        )
        // When: mapping to UI model
        val propertyUi: PropertyUi = PropertyUiMapper.mapToUi(propertyDomain)
        // Then: the UI model should reflect the same values as the domain
        val expectedPropertyUi = PropertyUi(
            id = 1,
            type = "House",
            price = 250000,
            address = "123 Main St",
            latitude = 0.0,
            longitude = 0.0,
            surface = 120,
            room = 5,
            bedroom = 3,
            description = "A nice house",
            entryDate = entryDate,
            soldDate = null,
            sold = false,
            agent = "Agent A",
            interestNearbySchool = true,
            interestNearbyShop = false,
            interestNearbyPark = true,
            interestNearbyRestaurant = false,
            interestNearbyPublicTransportation = true,
            interestNearbyPharmacy = false
        )
        assertEquals(expectedPropertyUi, propertyUi)
    }

    @Test
    fun `mapListToUi should correctly convert list of PropertyDomain to list of PropertyUi`() {
        // Given: a list of PropertyDomain instances
        val entryDate = Calendar.getInstance()
        val propertyDomains = listOf(
            PropertyDomain(
                id = 1,
                type = "House",
                price = 250000,
                address = "123 Main St",
                latitude = 0.0,
                longitude = 0.0,
                surface = 120,
                room = 5,
                bedroom = 3,
                description = "A nice house",
                entryDate = entryDate,
                soldDate = null,
                sold = false,
                agent = "Agent A",
                interestNearbySchool = true,
                interestNearbyShop = false,
                interestNearbyPark = true,
                interestNearbyRestaurant = false,
                interestNearbyPublicTransportation = true,
                interestNearbyPharmacy = false
            ),
            PropertyDomain(
                id = 2,
                type = "Apartment",
                price = 150000,
                address = "456 Market St",
                latitude = 1.0,
                longitude = 1.0,
                surface = 80,
                room = 4,
                bedroom = 2,
                description = "Modern apartment",
                entryDate = entryDate,
                soldDate = null,
                sold = false,
                agent = "Agent B",
                interestNearbySchool = false,
                interestNearbyShop = true,
                interestNearbyPark = false,
                interestNearbyRestaurant = true,
                interestNearbyPublicTransportation = false,
                interestNearbyPharmacy = true
            )
        )
        // When: mapping the list to UI models
        val propertyUiList = PropertyUiMapper.mapListToUi(propertyDomains)
        // Then: the resulting list should have the same size and corresponding field values
        assertEquals(2, propertyUiList.size)
        // Verify first element
        assertEquals(propertyDomains[0].id, propertyUiList[0].id)
        assertEquals(propertyDomains[0].type, propertyUiList[0].type)
        assertEquals(propertyDomains[0].price, propertyUiList[0].price)
        // Verify second element
        assertEquals(propertyDomains[1].id, propertyUiList[1].id)
        assertEquals(propertyDomains[1].type, propertyUiList[1].type)
        assertEquals(propertyDomains[1].price, propertyUiList[1].price)
    }

    @Test
    fun `mapToDomain should correctly convert PropertyUi to PropertyDomain`() {
        // Given: a PropertyUi instance with sample data
        val entryDate = Calendar.getInstance()
        val propertyUi = PropertyUi(
            id = 1,
            type = "House",
            price = 250000,
            address = "123 Main St",
            latitude = 0.0,
            longitude = 0.0,
            surface = 120,
            room = 5,
            bedroom = 3,
            description = "A nice house",
            entryDate = entryDate,
            soldDate = null,
            sold = false,
            agent = "Agent A",
            interestNearbySchool = true,
            interestNearbyShop = false,
            interestNearbyPark = true,
            interestNearbyRestaurant = false,
            interestNearbyPublicTransportation = true,
            interestNearbyPharmacy = false
        )
        // When: mapping to domain model
        val propertyDomain = PropertyUiMapper.mapToDomain(propertyUi)
        // Then: the domain model should match the expected values
        val expectedPropertyDomain = PropertyDomain(
            id = 1,
            type = "House",
            price = 250000,
            address = "123 Main St",
            latitude = 0.0,
            longitude = 0.0,
            surface = 120,
            room = 5,
            bedroom = 3,
            description = "A nice house",
            entryDate = entryDate,
            soldDate = null,
            sold = false,
            agent = "Agent A",
            interestNearbySchool = true,
            interestNearbyShop = false,
            interestNearbyPark = true,
            interestNearbyRestaurant = false,
            interestNearbyPublicTransportation = true,
            interestNearbyPharmacy = false
        )
        assertEquals(expectedPropertyDomain, propertyDomain)
    }

    @Test
    fun `mapListToDomain should correctly convert list of PropertyUi to list of PropertyDomain`() {
        // Given: a list of PropertyUi instances
        val entryDate = Calendar.getInstance()
        val propertyUiList = listOf(
            PropertyUi(
                id = 1,
                type = "House",
                price = 250000,
                address = "123 Main St",
                latitude = 0.0,
                longitude = 0.0,
                surface = 120,
                room = 5,
                bedroom = 3,
                description = "A nice house",
                entryDate = entryDate,
                soldDate = null,
                sold = false,
                agent = "Agent A",
                interestNearbySchool = true,
                interestNearbyShop = false,
                interestNearbyPark = true,
                interestNearbyRestaurant = false,
                interestNearbyPublicTransportation = true,
                interestNearbyPharmacy = false
            ),
            PropertyUi(
                id = 2,
                type = "Apartment",
                price = 150000,
                address = "456 Market St",
                latitude = 1.0,
                longitude = 1.0,
                surface = 80,
                room = 4,
                bedroom = 2,
                description = "Modern apartment",
                entryDate = entryDate,
                soldDate = null,
                sold = false,
                agent = "Agent B",
                interestNearbySchool = false,
                interestNearbyShop = true,
                interestNearbyPark = false,
                interestNearbyRestaurant = true,
                interestNearbyPublicTransportation = false,
                interestNearbyPharmacy = true
            )
        )
        // When: mapping the list to domain models
        val propertyDomains = PropertyUiMapper.mapListToDomain(propertyUiList)
        // Then: verify the size and that each domain model matches the expected values
        assertEquals(2, propertyDomains.size)

        val expectedDomain1 = PropertyDomain(
            id = 1,
            type = "House",
            price = 250000,
            address = "123 Main St",
            latitude = 0.0,
            longitude = 0.0,
            surface = 120,
            room = 5,
            bedroom = 3,
            description = "A nice house",
            entryDate = entryDate,
            soldDate = null,
            sold = false,
            agent = "Agent A",
            interestNearbySchool = true,
            interestNearbyShop = false,
            interestNearbyPark = true,
            interestNearbyRestaurant = false,
            interestNearbyPublicTransportation = true,
            interestNearbyPharmacy = false
        )
        val expectedDomain2 = PropertyDomain(
            id = 2,
            type = "Apartment",
            price = 150000,
            address = "456 Market St",
            latitude = 1.0,
            longitude = 1.0,
            surface = 80,
            room = 4,
            bedroom = 2,
            description = "Modern apartment",
            entryDate = entryDate,
            soldDate = null,
            sold = false,
            agent = "Agent B",
            interestNearbySchool = false,
            interestNearbyShop = true,
            interestNearbyPark = false,
            interestNearbyRestaurant = true,
            interestNearbyPublicTransportation = false,
            interestNearbyPharmacy = true
        )

        assertEquals(expectedDomain1, propertyDomains[0])
        assertEquals(expectedDomain2, propertyDomains[1])
    }
}