package com.xavier_carpentier.realestatemanager.domain.location

import android.location.Location
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

// Fake implementation of LocationRepository for testing purposes
class FakeLocationRepository : LocationRepository {
    // Given: a variable to hold a fake location, initially null
    var fakeLocation: Location? = null

    // When: getLastLocation is called, return the fake location
    override suspend fun getLastLocation(): Location? = fakeLocation
}

@RunWith(RobolectricTestRunner::class)
class GetLastLocationUseCaseTest {

    @Test
    fun `invoke returns location when repository returns valid location`() = runTest {
        // Given: a FakeLocationRepository that returns a valid Location
        val fakeRepository = FakeLocationRepository()
        val expectedLocation = Location("test_provider").apply {
            latitude = 48.8566
            longitude = 2.3522
        }
        fakeRepository.fakeLocation = expectedLocation

        // When: GetLastLocationUseCase is invoked
        val useCase = GetLastLocationUseCase(fakeRepository)
        val result = useCase.invoke()

        // Then: the result should be equal to the expected Location
        assertEquals(expectedLocation, result)
    }

    @Test
    fun `invoke returns null when repository returns null`() = runTest {
        // Given: a FakeLocationRepository that returns null
        val fakeRepository = FakeLocationRepository()
        fakeRepository.fakeLocation = null

        // When: GetLastLocationUseCase is invoked
        val useCase = GetLastLocationUseCase(fakeRepository)
        val result = useCase.invoke()

        // Then: the result should be null
        assertNull(result)
    }
}