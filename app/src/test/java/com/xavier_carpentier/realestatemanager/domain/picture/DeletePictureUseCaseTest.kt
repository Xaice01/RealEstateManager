package com.xavier_carpentier.realestatemanager.domain.picture
import com.xavier_carpentier.realestatemanager.domain.picture.model.PictureDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

// Fake implementation of PictureRepository that stores a list of pictures
class FakePictureRepositoryForDelete : PictureRepository {

    // //Given: A MutableStateFlow that holds a list of PictureDomain objects, initially empty
    private val _pictures = MutableStateFlow<List<PictureDomain>>(emptyList())

    // //When: getAllPicture is called, return the current list of pictures
    override fun getAllPicture(): StateFlow<List<PictureDomain>> = _pictures

    // No-op implementations for unused methods in this test
    override suspend fun insertPicture(pictureDomain: PictureDomain) {
        _pictures.value = _pictures.value + pictureDomain
    }
    override suspend fun updatePicture(pictureDomain: PictureDomain) { }
    override suspend fun upsertPicture(pictureDomain: PictureDomain) { }
    override fun getPicture(id: Int): StateFlow<PictureDomain?> = MutableStateFlow(null)
    override fun getAllPicturesByPropertyId(propertyId: Int): StateFlow<List<PictureDomain>?> = MutableStateFlow(null)

    // //When: deletePicture is called, remove the picture from the list (by matching its id)
    override suspend fun deletePicture(pictureDomain: PictureDomain) {
        _pictures.value = _pictures.value.filter { it.id != pictureDomain.id }
    }
}

class DeletePictureUseCaseTest {

    @Test
    fun `invoke deletes the picture from repository list`() = runTest {
        // //Given: a fake repository with a sample picture inserted
        val fakeRepository = FakePictureRepositoryForDelete()
        val samplePicture = PictureDomain(
            id = 1,
            propertyId = 100,
            description = "Test image",
            image = byteArrayOf(1, 2, 3)
        )
        // Insert the sample picture into the repository
        fakeRepository.insertPicture(samplePicture)
        // Verify that the picture is in the list
        assertTrue(fakeRepository.getAllPicture().value.contains(samplePicture))

        // //When: DeletePictureUseCase is invoked with the sample picture
        val deleteUseCase = DeletePictureUseCase(fakeRepository)
        deleteUseCase.invoke(samplePicture)

        // //Then: the repository's picture list should no longer contain the sample picture
        val remainingPictures = fakeRepository.getAllPicture().first()
        assertFalse(remainingPictures.contains(samplePicture))
    }
}