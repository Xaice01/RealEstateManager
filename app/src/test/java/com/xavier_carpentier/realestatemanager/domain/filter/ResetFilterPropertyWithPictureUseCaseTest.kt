package com.xavier_carpentier.realestatemanager.domain.filter

import com.xavier_carpentier.realestatemanager.domain.filter.model.FilterDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

// Fake FilterRepository for testing reset functionality
class FakeFilterRepositoryForReset : FilterRepository {
    private val _filterFlow = MutableStateFlow<FilterDomain?>(FilterDomain(
        types = null,
        minPrice = 100,
        maxPrice = 200,
        minSurface = 50,
        maxSurface = 150,
        sold = null,
        nearbySchool = true,
        nearbyShop = true,
        nearbyPark = true,
        nearbyRestaurant = true,
        nearbyPublicTransportation = true,
        nearbyPharmacy = true
    ))
    override fun getFilter(): StateFlow<FilterDomain?> = _filterFlow

    var resetCalled = false
    private val _filterIsApplied = MutableStateFlow(true)
    override fun filterIsApplied(): StateFlow<Boolean> = _filterIsApplied

    override fun setFilter(filter: FilterDomain) {
        _filterFlow.value = filter
        _filterIsApplied.value = true
    }

    override fun resetFilter() {
        // Given: When resetFilter is called, clear the filter and mark filter as not applied
        _filterFlow.value = null
        _filterIsApplied.value = false
        resetCalled = true
    }
}

class ResetFilterPropertyWithPictureUseCaseTest {

    @Test
    fun `invoke resets filter in repository`() = runTest {
        // Given: a fake FilterRepository that already has a filter applied
        val fakeRepository = FakeFilterRepositoryForReset()
        // Ensure repository initially has a filter
        assertEquals(true, fakeRepository.filterIsApplied().value)
        // When: ResetFilterPropertyWithPictureUseCase is invoked
        val useCase = ResetFilterPropertyWithPictureUseCase(fakeRepository)
        useCase.invoke()
        // Then: the repository's filter should be reset (null) and filterIsApplied should be false
        assertEquals(null, fakeRepository.getFilter().value)
        assertEquals(false, fakeRepository.filterIsApplied().value)
        assertEquals(true, fakeRepository.resetCalled)
    }
}