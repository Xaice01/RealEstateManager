package com.xavier_carpentier.realestatemanager.data.property

import com.xavier_carpentier.realestatemanager.datasource.property.Property
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyDomain
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar

class PropertyMapperTest {

    // Helper function to create a sample Calendar with a fixed time.
    private fun getFixedCalendar(timeInMillis: Long): Calendar {
        return Calendar.getInstance().apply { this.timeInMillis = timeInMillis }
    }

    // Helper function to create a sample Property data object.
    private fun getSampleProperty(): Property {
        val entryDate = getFixedCalendar(1000000000L)
        val soldDate = getFixedCalendar(2000000000L)
        return Property(
            id = 1,
            type = "Apartment",
            price = 250000,
            address = "123 Main St",
            latitude = 48.8566,
            longitude = 2.3522,
            surface = 80,
            room = 3,
            bedroom = 2,
            description = "Nice apartment",
            entryDate = entryDate,
            soldDate = soldDate,
            sold = false,
            agent = "John Doe",
            interestNearbySchool = true,
            interestNearbyShop = true,
            interestNearbyPark = false,
            interestNearbyRestaurant = true,
            interestNearbyPublicTransportation = true,
            interestNearbyPharmacy = false
        )
    }

    @Test
    fun `mapToDomain correctly maps Property to PropertyDomain`() {
        // Given: a sample Property object with known values
        val property = getSampleProperty()

        // When: mapping the Property to a PropertyDomain using mapToDomain
        val domain: PropertyDomain = PropertyMapper.mapToDomain(property)

        // Then: each field in the PropertyDomain should match the corresponding field in the original Property
        assertEquals(property.id, domain.id)
        assertEquals(property.type, domain.type)
        assertEquals(property.price, domain.price)
        assertEquals(property.address, domain.address)
        assertEquals(property.latitude, domain.latitude, 0.0)
        assertEquals(property.longitude, domain.longitude, 0.0)
        assertEquals(property.surface, domain.surface)
        assertEquals(property.room, domain.room)
        assertEquals(property.bedroom, domain.bedroom)
        assertEquals(property.description, domain.description)
        // Compare Calendar fields by checking timeInMillis values
        assertEquals(property.entryDate.timeInMillis, domain.entryDate.timeInMillis)
        assertEquals(property.soldDate?.timeInMillis, domain.soldDate?.timeInMillis)
        assertEquals(property.sold, domain.sold)
        assertEquals(property.agent, domain.agent)
        assertEquals(property.interestNearbySchool, domain.interestNearbySchool)
        assertEquals(property.interestNearbyShop, domain.interestNearbyShop)
        assertEquals(property.interestNearbyPark, domain.interestNearbyPark)
        assertEquals(property.interestNearbyRestaurant, domain.interestNearbyRestaurant)
        assertEquals(property.interestNearbyPublicTransportation, domain.interestNearbyPublicTransportation)
        assertEquals(property.interestNearbyPharmacy, domain.interestNearbyPharmacy)
    }

    @Test
    fun `mapListToDomain correctly maps list of Property to list of PropertyDomain`() {
        // Given: a list of sample Property objects
        val property1 = getSampleProperty()
        val property2 = getSampleProperty().copy(id = 2, type = "House")
        val properties = listOf(property1, property2)

        // When: mapping the list using mapListToDomain
        val domainList: List<PropertyDomain> = PropertyMapper.mapListToDomain(properties)

        // Then: the size of the list should match and each mapped domain object should have matching fields
        assertEquals(properties.size, domainList.size)
        properties.zip(domainList).forEach { (property, domain) ->
            assertEquals(property.id, domain.id)
            assertEquals(property.type, domain.type)
            // Additional field comparisons can be added as shown in the previous test
        }
    }

    @Test
    fun `mapToData correctly maps PropertyDomain to Property`() {
        // Given: a sample Property object and its corresponding domain mapping
        val property = getSampleProperty()
        val domain: PropertyDomain = PropertyMapper.mapToDomain(property)

        // When: mapping the PropertyDomain back to a Property using mapToData
        val dataProperty: Property = PropertyMapper.mapToData(domain)

        // Then: each field in the data Property should match the original domain fields
        assertEquals(domain.id, dataProperty.id)
        assertEquals(domain.type, dataProperty.type)
        assertEquals(domain.price, dataProperty.price)
        assertEquals(domain.address, dataProperty.address)
        assertEquals(domain.latitude, dataProperty.latitude, 0.0)
        assertEquals(domain.longitude, dataProperty.longitude, 0.0)
        assertEquals(domain.surface, dataProperty.surface)
        assertEquals(domain.room, dataProperty.room)
        assertEquals(domain.bedroom, dataProperty.bedroom)
        assertEquals(domain.description, dataProperty.description)
        // Compare Calendar fields by checking timeInMillis values
        assertEquals(domain.entryDate.timeInMillis, dataProperty.entryDate.timeInMillis)
        assertEquals(domain.soldDate?.timeInMillis, dataProperty.soldDate?.timeInMillis)
        assertEquals(domain.sold, dataProperty.sold)
        assertEquals(domain.agent, dataProperty.agent)
        assertEquals(domain.interestNearbySchool, dataProperty.interestNearbySchool)
        assertEquals(domain.interestNearbyShop, dataProperty.interestNearbyShop)
        assertEquals(domain.interestNearbyPark, dataProperty.interestNearbyPark)
        assertEquals(domain.interestNearbyRestaurant, dataProperty.interestNearbyRestaurant)
        assertEquals(domain.interestNearbyPublicTransportation, dataProperty.interestNearbyPublicTransportation)
        assertEquals(domain.interestNearbyPharmacy, dataProperty.interestNearbyPharmacy)
    }

    @Test
    fun `mapListToData correctly maps list of PropertyDomain to list of Property`() {
        // Given: a list of sample PropertyDomain objects obtained by mapping a list of Property objects
        val property1 = getSampleProperty()
        val property2 = getSampleProperty().copy(id = 2, type = "House")
        val properties = listOf(property1, property2)
        val domainList: List<PropertyDomain> = PropertyMapper.mapListToDomain(properties)

        // When: mapping the list of PropertyDomain objects back to a list of Property using mapListToData
        val dataList: List<Property> = PropertyMapper.mapListToData(domainList)

        // Then: the size of the list should match and each mapped data object should match the corresponding domain object
        assertEquals(domainList.size, dataList.size)
        domainList.zip(dataList).forEach { (domain, data) ->
            assertEquals(domain.id, data.id)
            assertEquals(domain.type, data.type)
            // Additional field comparisons can be added as needed
        }
    }
}