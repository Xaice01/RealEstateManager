package com.xavier_carpentier.realestatemanager.data.filter

import com.xavier_carpentier.realestatemanager.data.filter.model.FilterMapper
import com.xavier_carpentier.realestatemanager.domain.filter.model.FilterDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PropertiesFilterRepositoryImplTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: PropertiesFilterRepositoryImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = PropertiesFilterRepositoryImpl(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test initial filter state and applied flag`() = runTest {
        // Given
        // When
        val filterFlow = repository.getFilter()
        val appliedFlow = repository.filterIsApplied()
        // Then
        assertEquals(null, filterFlow.value)
        assertEquals(false, appliedFlow.value)
    }

    @Test
    fun `test setFilter updates filter state and applied flag`() = runTest {
        // Given
        val filterMapper = FilterMapper()
        val sampleFilter = FilterDomain(
            types = null,
            minPrice = 120000,
            maxPrice = 550000,
            minSurface = 40,
            maxSurface = 180,
            sold = false,
            nearbySchool = true,
            nearbyShop = false,
            nearbyPark = true,
            nearbyRestaurant = false,
            nearbyPublicTransportation = true,
            nearbyPharmacy = false
        )

        val filterJob = launch { repository.filterStateFlow.collect { } }
        val appliedJob = launch { repository.filterIsApplied().collect { } }

        // When
        repository.setFilter(sampleFilter)
        advanceUntilIdle()

        // Then
        assertEquals(sampleFilter, filterMapper.mapToDomain(repository.filterStateFlow.value!!))
        assertEquals(true, repository.filterIsApplied().value)

        filterJob.cancel()
        appliedJob.cancel()
    }

    @Test
    fun `test resetFilter clears filter state and applied flag`() = runTest {
        // Given
        val sampleFilter = FilterDomain(
            types = null,
            minPrice = 130000,
            maxPrice = 600000,
            minSurface = 45,
            maxSurface = 190,
            sold = true,
            nearbySchool = false,
            nearbyShop = true,
            nearbyPark = false,
            nearbyRestaurant = true,
            nearbyPublicTransportation = false,
            nearbyPharmacy = true
        )

        val filterJob = launch { repository.getFilter().collect { } }
        val appliedJob = launch { repository.filterIsApplied().collect { } }
        repository.setFilter(sampleFilter)
        advanceUntilIdle()

        // When
        repository.resetFilter()
        advanceUntilIdle()

        // Then
        assertEquals(null, repository.getFilter().value)
        assertEquals(false, repository.filterIsApplied().value)

        filterJob.cancel()
        appliedJob.cancel()
    }
}