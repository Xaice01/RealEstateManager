package com.xavier_carpentier.realestatemanager.ui.mapper

import com.xavier_carpentier.realestatemanager.domain.picture.model.PictureDomain
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyDomain
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain
import com.xavier_carpentier.realestatemanager.ui.model.PropertyWithPictureUi
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.Calendar

class PropertyWithPictureUiMapperTest {

    @Test
    fun `mapToUi should correctly convert PropertyWithPictureDomain to PropertyWithPictureUi`() {
        // Given: a PropertyWithPictureDomain instance with sample data
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
        val pictureDomain = PictureDomain(
            id = 1,
            propertyId = 1,
            description = "Front view",
            image = byteArrayOf(1, 2, 3)
        )
        val propertyWithPictureDomain = PropertyWithPictureDomain(propertyDomain, listOf(pictureDomain))
        // When: mapping to UI model
        val propertyWithPictureUi: PropertyWithPictureUi = PropertyWithPictureUiMapper.mapToUi(propertyWithPictureDomain)
        // Then: the UI model should correctly represent the domain data
        assertEquals(propertyDomain.id, propertyWithPictureUi.propertyUi.id)
        assertEquals(propertyDomain.type, propertyWithPictureUi.propertyUi.type)
        assertEquals(1, propertyWithPictureUi.picturesUi.size)
        assertEquals(pictureDomain.description, propertyWithPictureUi.picturesUi[0].description)
    }

    @Test
    fun `mapListToUi should correctly convert list of PropertyWithPictureDomain to list of PropertyWithPictureUi`() {
        // Given: a list of PropertyWithPictureDomain instances
        val entryDate = Calendar.getInstance()
        val propertyDomain1 = PropertyDomain(
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
        val propertyDomain2 = PropertyDomain(
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
        val pictureDomain = PictureDomain(
            id = 1,
            propertyId = 1,
            description = "Front view",
            image = byteArrayOf(1, 2, 3)
        )
        val listDomain = listOf(
            PropertyWithPictureDomain(propertyDomain1, listOf(pictureDomain)),
            PropertyWithPictureDomain(propertyDomain2, listOf())
        )
        // When: mapping the list to UI models
        val uiList = PropertyWithPictureUiMapper.mapListToUi(listDomain)
        // Then: verify the list size and that each element is correctly mapped
        assertEquals(2, uiList.size)
        assertEquals(propertyDomain1.id, uiList[0].propertyUi.id)
        assertEquals(propertyDomain2.id, uiList[1].propertyUi.id)
    }

    @Test
    fun `mapToDomain should correctly convert PropertyWithPictureUi to PropertyWithPictureDomain`() {
        // Given: a PropertyWithPictureUi instance with sample data
        val entryDate = Calendar.getInstance()
        val propertyUi = com.xavier_carpentier.realestatemanager.ui.model.PropertyUi(
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
        val pictureUi = com.xavier_carpentier.realestatemanager.ui.model.PictureUi(
            id = 1,
            propertyId = 1,
            description = "Front view",
            image = byteArrayOf(1, 2, 3)
        )
        val propertyWithPictureUi = PropertyWithPictureUi(
            propertyUi = propertyUi,
            picturesUi = listOf(pictureUi)
        )
        // When: mapping to domain model
        val domain = PropertyWithPictureUiMapper.mapToDomain(propertyWithPictureUi)
        // Then: the domain model should match the expected values
        assertEquals(propertyUi.id, domain.propertyDomain.id)
        assertEquals(1, domain.pictureDomains.size)
        assertEquals(pictureUi.description, domain.pictureDomains[0].description)
    }
}