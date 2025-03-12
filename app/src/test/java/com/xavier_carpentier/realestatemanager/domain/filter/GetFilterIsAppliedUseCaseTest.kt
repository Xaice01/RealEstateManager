package com.xavier_carpentier.realestatemanager.domain.filter

import com.xavier_carpentier.realestatemanager.domain.filter.model.FilterDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

// Fake FilterRepository for testing filterIsApplied functionality
class FakeFilterRepositoryForIsApplied : FilterRepository {
    private val _filterIsApplied = MutableStateFlow(false)
    override fun filterIsApplied(): StateFlow<Boolean> = _filterIsApplied

    // For this test, we don't need a full implementation for getFilter and setFilter
    private val _filterFlow = MutableStateFlow<FilterDomain?>(null)
    override fun getFilter(): StateFlow<FilterDomain?> = _filterFlow
    override fun setFilter(filter: FilterDomain) {
        _filterFlow.value = filter
        _filterIsApplied.value = true
    }
    override fun resetFilter() {
        _filterFlow.value = null
        _filterIsApplied.value = false
    }
}

class GetFilterIsAppliedUseCaseTest {

    @Test
    fun `invoke returns false when no filter is applied`() = runTest {
        // Given: a fake FilterRepository with filterIsApplied initially false
        val fakeRepository = FakeFilterRepositoryForIsApplied()
        // When: GetFilterIsAppliedUseCase is invoked
        val useCase = GetFilterIsAppliedUseCase(fakeRepository)
        // Then: the returned flow should have false
        assertEquals(false, useCase.invoke().value)
    }

    @Test
    fun `invoke returns true when a filter is applied`() = runTest {
        // Given: a fake FilterRepository where a filter has been set (thus applied)
        val fakeRepository = FakeFilterRepositoryForIsApplied()
        val sampleFilter = FilterDomain(
            types = null,
            minPrice = 100,
            maxPrice = 200,
            minSurface = 50,
            maxSurface = 150,
            sold = true,
            nearbySchool = true,
            nearbyShop = true,
            nearbyPark = true,
            nearbyRestaurant = true,
            nearbyPublicTransportation = true,
            nearbyPharmacy = true
        )
        fakeRepository.setFilter(sampleFilter)
        // When: GetFilterIsAppliedUseCase is invoked
        val useCase = GetFilterIsAppliedUseCase(fakeRepository)
        // Then: the returned flow should have true
        assertEquals(true, useCase.invoke().value)
    }
}