package com.xavier_carpentier.realestatemanager.domain.picture

import com.xavier_carpentier.realestatemanager.domain.picture.model.PictureDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

// Fake implementation of PictureRepository for testing InsertPictureUseCase
class FakePictureRepositoryForInsert : PictureRepository {
    // //Given: A variable to store the inserted picture
    var insertedPicture: PictureDomain? = null

    override suspend fun insertPicture(pictureDomain: PictureDomain) {
        // //When: insertPicture is called, store the picture in insertedPicture
        insertedPicture = pictureDomain
    }

    // No-op implementations for the other methods
    override suspend fun updatePicture(pictureDomain: PictureDomain) {}
    override suspend fun upsertPicture(pictureDomain: PictureDomain) {}
    override suspend fun deletePicture(pictureDomain: PictureDomain) {}
    override fun getPicture(id: Int): StateFlow<PictureDomain?> = MutableStateFlow(null)
    override fun getAllPicturesByPropertyId(propertyId: Int): StateFlow<List<PictureDomain>?> = MutableStateFlow(null)
    override fun getAllPicture(): StateFlow<List<PictureDomain>> = MutableStateFlow(emptyList())
}

class InsertPictureUseCaseTest {

    @Test
    fun `invoke calls repository insertPicture with provided picture`() = runTest {
        // //Given: a fake repository and a sample PictureDomain object
        val fakeRepository = FakePictureRepositoryForInsert()
        val useCase = InsertPictureUseCase(fakeRepository)
        val samplePicture = PictureDomain(
            id = 1,
            propertyId = 100,
            description = "Test image",
            image = byteArrayOf(1, 2, 3)
        )

        // //When: invoking the use case with the sample picture
        useCase.invoke(samplePicture)

        // //Then: the repository's insertedPicture should be equal to the sample picture
        assertEquals(samplePicture, fakeRepository.insertedPicture)
    }
}