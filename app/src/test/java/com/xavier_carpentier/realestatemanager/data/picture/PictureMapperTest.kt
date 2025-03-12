package com.xavier_carpentier.realestatemanager.data.picture

import com.xavier_carpentier.realestatemanager.datasource.picture.Picture
import com.xavier_carpentier.realestatemanager.domain.picture.model.PictureDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PictureMapperTest {

    @Test
    fun `mapToDomain correctly maps Picture to PictureDomain`() {
        // Given: a Picture object with sample data
        val picture = Picture(
            id = 1,
            propertyId = 100,
            description = "Test Picture",
            imageData = byteArrayOf(0, 1, 2, 3)
        )
        // When: mapping the Picture object to a PictureDomain
        val result = PictureMapper.mapToDomain(picture)
        // Then: the resulting PictureDomain should have the same properties as the Picture
        assertEquals(picture.id, result.id)
        assertEquals(picture.propertyId, result.propertyId)
        assertEquals(picture.description, result.description)
        assertTrue(picture.imageData.contentEquals(result.image))
    }

    @Test
    fun `mapListToDomain correctly maps a list of Picture to a list of PictureDomain`() {
        // Given: a list of Picture objects with sample data
        val pictures = listOf(
            Picture(
                id = 1,
                propertyId = 100,
                description = "Pic1",
                imageData = byteArrayOf(1, 2, 3)
            ),
            Picture(
                id = 2,
                propertyId = 200,
                description = "Pic2",
                imageData = byteArrayOf(4, 5, 6)
            )
        )
        // When: mapping the list of Picture objects to a list of PictureDomain objects
        val resultList = PictureMapper.mapListToDomain(pictures)
        // Then: each element in the result list should correctly correspond to the original Picture
        assertEquals(pictures.size, resultList.size)
        pictures.zip(resultList).forEach { (picture, domain) ->
            assertEquals(picture.id, domain.id)
            assertEquals(picture.propertyId, domain.propertyId)
            assertEquals(picture.description, domain.description)
            assertTrue(picture.imageData.contentEquals(domain.image))
        }
    }

    @Test
    fun `mapToData correctly maps PictureDomain to Picture`() {
        // Given: a PictureDomain object with sample data
        val pictureDomain = PictureDomain(
            id = 1,
            propertyId = 100,
            description = "Test Picture Domain",
            image = byteArrayOf(7, 8, 9)
        )
        // When: mapping the PictureDomain object to a Picture
        val result = PictureMapper.mapToData(pictureDomain)
        // Then: the resulting Picture should have the same properties as the PictureDomain
        assertEquals(pictureDomain.id, result.id)
        assertEquals(pictureDomain.propertyId, result.propertyId)
        assertEquals(pictureDomain.description, result.description)
        assertTrue(pictureDomain.image.contentEquals(result.imageData))
    }

    @Test
    fun `mapListToData correctly maps a list of PictureDomain to a list of Picture`() {
        // Given: a list of PictureDomain objects with sample data
        val pictureDomains = listOf(
            PictureDomain(
                id = 1,
                propertyId = 100,
                description = "Domain Pic1",
                image = byteArrayOf(10, 11, 12)
            ),
            PictureDomain(
                id = 2,
                propertyId = 200,
                description = "Domain Pic2",
                image = byteArrayOf(13, 14, 15)
            )
        )
        // When: mapping the list of PictureDomain objects to a list of Picture objects
        val resultList = PictureMapper.mapListToData(pictureDomains)
        // Then: each element in the result list should correctly correspond to the original PictureDomain
        assertEquals(pictureDomains.size, resultList.size)
        pictureDomains.zip(resultList).forEach { (domain, picture) ->
            assertEquals(domain.id, picture.id)
            assertEquals(domain.propertyId, picture.propertyId)
            assertEquals(domain.description, picture.description)
            assertTrue(domain.image.contentEquals(picture.imageData))
        }
    }
}