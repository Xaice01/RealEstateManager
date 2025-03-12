package com.xavier_carpentier.realestatemanager.domain.filter

import com.xavier_carpentier.realestatemanager.domain.filter.model.FilterDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

// Fake FilterRepository for testing GetFilterPropertyWithPictureAsFLowUseCase
class FakeFilterRepositoryForGetFilter : FilterRepository {
    private val _filterFlow = MutableStateFlow<FilterDomain?>(null)
    override fun getFilter(): StateFlow<FilterDomain?> = _filterFlow

    override fun setFilter(filter: FilterDomain) {
        _filterFlow.value = filter
    }

    // For this test we don't need to simulate filterIsApplied
    private val _filterIsApplied = MutableStateFlow(false)
    override fun filterIsApplied(): StateFlow<Boolean> = _filterIsApplied

    override fun resetFilter() {
        _filterFlow.value = null
        _filterIsApplied.value = false
    }
}

class GetFilterPropertyWithPictureAsFLowUseCaseTest {

    @Test
    fun `invoke returns Empty result when filter is null`() = runTest {
        // Given: a fake FilterRepository with no filter set (null)
        val fakeRepository = FakeFilterRepositoryForGetFilter()
        // When: GetFilterPropertyWithPictureAsFLowUseCase is invoked
        val useCase = GetFilterPropertyWithPictureAsFLowUseCase(fakeRepository)
        val result = useCase.invoke().first()
        // Then: the result should be Empty
        assertTrue(result is GetFilterPropertyWithPictureUseCaseResult.Empty)
    }

    @Test
    fun `invoke returns Success result when filter is set`() = runTest {
        // Given: a fake FilterRepository with a filter applied
        val fakeRepository = FakeFilterRepositoryForGetFilter()
        val sampleFilter = FilterDomain(
            types = null,
            minPrice = 100,
            maxPrice = 200,
            minSurface = 50,
            maxSurface = 150,
            sold = false,
            nearbySchool = true,
            nearbyShop = false,
            nearbyPark = true,
            nearbyRestaurant = false,
            nearbyPublicTransportation = true,
            nearbyPharmacy = false
        )
        fakeRepository.setFilter(sampleFilter)
        // When: GetFilterPropertyWithPictureAsFLowUseCase is invoked
        val useCase = GetFilterPropertyWithPictureAsFLowUseCase(fakeRepository)
        val result = useCase.invoke().first()
        // Then: the result should be Success containing the sample filter
        assertTrue(result is GetFilterPropertyWithPictureUseCaseResult.Success)
        if (result is GetFilterPropertyWithPictureUseCaseResult.Success) {
            assertTrue(result.filter == sampleFilter)
        }
    }
}