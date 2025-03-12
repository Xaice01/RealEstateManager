package com.xavier_carpentier.realestatemanager.data.property

import com.xavier_carpentier.realestatemanager.data.picture.PictureMapper
import com.xavier_carpentier.realestatemanager.datasource.picture.Picture
import com.xavier_carpentier.realestatemanager.datasource.property.Property
import com.xavier_carpentier.realestatemanager.datasource.property.PropertyWithPicture
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar

class PropertyWithPictureMapperTest {

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

    // Helper function to create a sample Picture data object.
    private fun getSamplePicture(): Picture {
        return Picture(
            id = 1,
            propertyId = 1,
            description = "Front view",
            imageData = byteArrayOf(1, 2, 3)
        )
    }

    // Helper function to create a sample PropertyWithPicture data object.
    private fun getSamplePropertyWithPicture(): PropertyWithPicture {
        return PropertyWithPicture(
            property = getSampleProperty(),
            picture = listOf(getSamplePicture())
        )
    }

    @Test
    fun `mapToDomain correctly maps PropertyWithPicture to PropertyWithPictureDomain`() {
        // Given: a sample PropertyWithPicture object with a property and a list of pictures
        val propertyWithPicture = getSamplePropertyWithPicture()

        // When: mapping the PropertyWithPicture to PropertyWithPictureDomain using mapToDomain
        val domain: PropertyWithPictureDomain = PropertyWithPictureMapper.mapToDomain(propertyWithPicture)

        // Then: the domain object's property and picture list should be mapped correctly
        val expectedPropertyDomain = PropertyMapper.mapToDomain(propertyWithPicture.property)
        val expectedPictureDomains = PictureMapper.mapListToDomain(propertyWithPicture.picture)
        assertEquals(expectedPropertyDomain, domain.propertyDomain)
        assertEquals(expectedPictureDomains, domain.pictureDomains)
    }

    @Test
    fun `mapListToDomain correctly maps list of PropertyWithPicture to list of PropertyWithPictureDomain`() {
        // Given: a list of sample PropertyWithPicture objects
        val propertyWithPicture1 = getSamplePropertyWithPicture()
        val propertyWithPicture2 = getSamplePropertyWithPicture().copy(
            property = getSampleProperty().copy(id = 2, type = "House")
        )
        val list = listOf(propertyWithPicture1, propertyWithPicture2)

        // When: mapping the list using mapListToDomain
        val domainList: List<PropertyWithPictureDomain> = PropertyWithPictureMapper.mapListToDomain(list)

        // Then: each element in the list should be mapped correctly
        assertEquals(list.size, domainList.size)
        list.zip(domainList).forEach { (dataItem, domainItem) ->
            val expectedPropertyDomain = PropertyMapper.mapToDomain(dataItem.property)
            val expectedPictureDomains = PictureMapper.mapListToDomain(dataItem.picture)
            assertEquals(expectedPropertyDomain, domainItem.propertyDomain)
            assertEquals(expectedPictureDomains, domainItem.pictureDomains)
        }
    }

    @Test
    fun `mapToData correctly maps PropertyWithPictureDomain to PropertyWithPicture`() {
        // Given: a sample PropertyWithPicture object and its corresponding domain mapping
        val propertyWithPicture = getSamplePropertyWithPicture()
        val domain: PropertyWithPictureDomain = PropertyWithPictureMapper.mapToDomain(propertyWithPicture)

        // When: mapping the PropertyWithPictureDomain back to PropertyWithPicture using mapToData
        val dataItem: PropertyWithPicture = PropertyWithPictureMapper.mapToData(domain)

        // Then: the data object's property and picture list should match the original domain mapping
        val expectedProperty = PropertyMapper.mapToData(domain.propertyDomain)
        val expectedPictures = PictureMapper.mapListToData(domain.pictureDomains)
        assertEquals(expectedProperty, dataItem.property)
        assertEquals(expectedPictures, dataItem.picture)
    }

    @Test
    fun `mapListToData correctly maps list of PropertyWithPictureDomain to list of PropertyWithPicture`() {
        // Given: a list of sample PropertyWithPictureDomain objects obtained by mapping a list of PropertyWithPicture objects
        val propertyWithPicture1 = getSamplePropertyWithPicture()
        val propertyWithPicture2 = getSamplePropertyWithPicture().copy(
            property = getSampleProperty().copy(id = 2, type = "House")
        )
        val list = listOf(propertyWithPicture1, propertyWithPicture2)
        val domainList: List<PropertyWithPictureDomain> = PropertyWithPictureMapper.mapListToDomain(list)

        // When: mapping the list of PropertyWithPictureDomain objects back to a list of PropertyWithPicture using mapListToData
        val dataList: List<PropertyWithPicture> = PropertyWithPictureMapper.mapListToData(domainList)

        // Then: the size of the list should match and each mapped data object should match the corresponding domain mapping
        assertEquals(domainList.size, dataList.size)
        domainList.zip(dataList).forEach { (domainItem, dataItem) ->
            val expectedProperty = PropertyMapper.mapToData(domainItem.propertyDomain)
            val expectedPictures = PictureMapper.mapListToData(domainItem.pictureDomains)
            assertEquals(expectedProperty, dataItem.property)
            assertEquals(expectedPictures, dataItem.picture)
        }
    }
}