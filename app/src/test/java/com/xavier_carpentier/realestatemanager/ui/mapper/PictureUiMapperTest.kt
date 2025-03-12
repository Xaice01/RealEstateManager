package com.xavier_carpentier.realestatemanager.ui.mapper

import com.xavier_carpentier.realestatemanager.domain.picture.model.PictureDomain
import com.xavier_carpentier.realestatemanager.ui.model.PictureUi
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PictureUiMapperTest {

    @Test
    fun `mapToUi should correctly convert PictureDomain to PictureUi`() {
        // Given: a PictureDomain instance with sample data
        val imageBytes = byteArrayOf(1, 2, 3)
        val pictureDomain = PictureDomain(id = 1, propertyId = 10, description = "Test Image", image = imageBytes)
        // When: mapping to UI model
        val pictureUi: PictureUi = PictureUiMapper.mapToUi(pictureDomain)
        // Then: the UI model should have the same field values as the domain
        assertEquals(1, pictureUi.id)
        assertEquals(10, pictureUi.propertyId)
        assertEquals("Test Image", pictureUi.description)
        assertEquals(imageBytes, pictureUi.image)
    }

    @Test
    fun `mapListToUi should correctly convert list of PictureDomain to list of PictureUi`() {
        // Given: a list of PictureDomain instances
        val imageBytes = byteArrayOf(1, 2, 3)
        val pictureDomains = listOf(
            PictureDomain(1, 10, "Test Image 1", imageBytes),
            PictureDomain(2, 20, "Test Image 2", imageBytes)
        )
        // When: mapping the list to UI models
        val pictureUiList = PictureUiMapper.mapListToUi(pictureDomains)
        // Then: verify the list size and each mapped element
        assertEquals(2, pictureUiList.size)
        assertEquals(1, pictureUiList[0].id)
        assertEquals(10, pictureUiList[0].propertyId)
        assertEquals("Test Image 1", pictureUiList[0].description)
        assertEquals(2, pictureUiList[1].id)
        assertEquals(20, pictureUiList[1].propertyId)
        assertEquals("Test Image 2", pictureUiList[1].description)
    }

    @Test
    fun `mapToDomain should correctly convert PictureUi to PictureDomain`() {
        // Given: a PictureUi instance with sample data
        val imageBytes = byteArrayOf(4, 5, 6)
        val pictureUi = PictureUi(id = 3, propertyId = 30, description = "Test Image 3", image = imageBytes)
        // When: mapping to domain model
        val pictureDomain: PictureDomain = PictureUiMapper.mapToDomain(pictureUi)
        // Then: the domain model should have the same field values as the UI model
        assertEquals(3, pictureDomain.id)
        assertEquals(30, pictureDomain.propertyId)
        assertEquals("Test Image 3", pictureDomain.description)
        assertEquals(imageBytes, pictureDomain.image)
    }

    @Test
    fun `mapListToDomain should correctly convert list of PictureUi to list of PictureDomain`() {
        // Given: a list of PictureUi instances with sample data
        val imageBytes = byteArrayOf(7, 8, 9)
        val pictureUiList = listOf(
            PictureUi(4, 40, "Test Image 4", imageBytes),
            PictureUi(5, 50, "Test Image 5", imageBytes)
        )
        // When: mapping the list to domain models
        val pictureDomains = PictureUiMapper.mapListToDomain(pictureUiList)
        // Then: verify the list size and that each element is correctly mapped
        assertEquals(2, pictureDomains.size)
        assertEquals(4, pictureDomains[0].id)
        assertEquals(40, pictureDomains[0].propertyId)
        assertEquals("Test Image 4", pictureDomains[0].description)
        assertEquals(5, pictureDomains[1].id)
        assertEquals(50, pictureDomains[1].propertyId)
        assertEquals("Test Image 5", pictureDomains[1].description)
    }
}