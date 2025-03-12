package com.xavier_carpentier.realestatemanager.data.location

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocationRepositoryImplTest {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRepository: LocationRepositoryImpl

    @Before
    fun setup() {

        fusedLocationProviderClient = mockk(relaxed = true)
        locationRepository = LocationRepositoryImpl(fusedLocationProviderClient)
    }

    @Test
    fun `getLastLocation returns location when task is successful`() = runBlocking {
        // Given
        val fakeLocation = Location("dummy").apply {
            latitude = 48.8566
            longitude = 2.3522
        }
        // Given
        val fakeTask = mockk<Task<Location>>()
        every { fusedLocationProviderClient.lastLocation } returns fakeTask
        every { fakeTask.addOnSuccessListener(any()) } answers {
            // When
            val listener = firstArg<OnSuccessListener<Location>>()
            listener.onSuccess(fakeLocation)
            fakeTask
        }
        every { fakeTask.addOnFailureListener(any()) } returns fakeTask

        // When
        val result = locationRepository.getLastLocation()

        // Then
        assertNotNull(result)
        assertEquals(fakeLocation, result)
    }

    @Test
    fun `getLastLocation returns null when task fails`() = runBlocking {
        // Given
        val fakeTask = mockk<Task<Location>>()
        every { fusedLocationProviderClient.lastLocation } returns fakeTask
        every { fakeTask.addOnSuccessListener(any()) } returns fakeTask
        every { fakeTask.addOnFailureListener(any()) } answers {
            // When
            val listener = firstArg<OnFailureListener>()
            listener.onFailure(Exception("Test Exception"))
            fakeTask
        }

        // When
        val result = locationRepository.getLastLocation()

        // Then
        assertNull(result)
    }
}