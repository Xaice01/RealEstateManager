package com.xavier_carpentier.realestatemanager.domain.filter

import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import com.xavier_carpentier.realestatemanager.domain.current_setting.CurrentSettingRepository
import com.xavier_carpentier.realestatemanager.domain.current_setting.GetCurrentDollarRateUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.GetSelectedCurrencyUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.model.CurrencyType
import com.xavier_carpentier.realestatemanager.domain.current_setting.utils.ConversePrice
import com.xavier_carpentier.realestatemanager.domain.filter.model.FilterDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

// Fake FilterRepository implementation for testing SetFilterPropertyWithPictureUseCase
class FakeFilterRepository : FilterRepository {
    // Given: a MutableStateFlow to hold the filter (initially null)
    private val _filterFlow = MutableStateFlow<FilterDomain?>(null)
    override fun getFilter(): StateFlow<FilterDomain?> = _filterFlow

    // Store the last filter set
    var storedFilter: FilterDomain? = null
    private val _filterIsApplied = MutableStateFlow(false)
    override fun setFilter(filter: FilterDomain) {
        storedFilter = filter
        _filterFlow.value = filter
        _filterIsApplied.value = true
    }
    override fun resetFilter() {
        storedFilter = null
        _filterFlow.value = null
        _filterIsApplied.value = false
    }
    override fun filterIsApplied(): StateFlow<Boolean> = _filterIsApplied
}

// Fake CurrentSettingRepository for ConversePrice testing
class FakeCurrentSettingRepositoryForConversePrice : CurrentSettingRepository {
    // Given: no agent is needed for this test
    private val _currentAgentFlow = MutableStateFlow<AgentDomain?>(null)
    override val currentAgentFlow: StateFlow<AgentDomain?> = _currentAgentFlow

    // Given: we set the selected currency to EURO to trigger conversion
    private val _selectedCurrencyFlow = MutableStateFlow<CurrencyType?>(CurrencyType.EURO)
    override val selectedCurrencyFlow: StateFlow<CurrencyType?> = _selectedCurrencyFlow

    // Given: we set the current dollar rate to 2.0f so that conversion divides the price by 2.0
    private val _currentDollarRate = MutableStateFlow<Float?>(2.0f)
    override val currentDollarRate: StateFlow<Float?> = _currentDollarRate

    override fun setCurrentAgent(currentAgent: AgentDomain) { }
    override fun setSelectedCurrency(currencyType: CurrencyType) { }
    override fun setCurrentDollarRate(currentRate: Float) { }
}

class SetFilterPropertyWithPictureUseCaseTest {

    @Test
    fun `invoke converts prices and sets filter in repository`() = runTest {
        // Given: a fake FilterRepository
        val fakeFilterRepository = FakeFilterRepository()

        // And given: a fake CurrentSettingRepository with EURO currency and dollar rate 2.0
        val fakeCurrentSettingRepository = FakeCurrentSettingRepositoryForConversePrice()
        val getCurrentDollarRateUseCase = GetCurrentDollarRateUseCase(fakeCurrentSettingRepository)
        val getSelectedCurrencyUseCase = GetSelectedCurrencyUseCase(fakeCurrentSettingRepository)
        // When: creating an instance of the real ConversePrice (cannot be subclassed)
        val conversePrice = ConversePrice(getCurrentDollarRateUseCase, getSelectedCurrencyUseCase)

        // And given: a sample FilterDomain with original minPrice and maxPrice values
        val originalFilter = FilterDomain(
            types = null,
            minPrice = 100, // original value
            maxPrice = 200, // original value
            minSurface = 50,
            maxSurface = 150,
            sold = null,
            nearbySchool = true,
            nearbyShop = false,
            nearbyPark = true,
            nearbyRestaurant = false,
            nearbyPublicTransportation = true,
            nearbyPharmacy = false
        )

        // When: SetFilterPropertyWithPictureUseCase is invoked with the sample filter
        val useCase = SetFilterPropertyWithPictureUseCase(fakeFilterRepository, conversePrice)
        useCase.invoke(originalFilter)

        // Then: The repository's stored filter should have minPrice and maxPrice converted
        // Calculation: For EURO, conversion is Math.round(price / currentDollarRate)
        // For 100: Math.round(100 / 2.0) = 50; For 200: Math.round(200 / 2.0) = 100
        val expectedFilter = originalFilter.copy(minPrice = 50, maxPrice = 100)
        assertEquals(expectedFilter, fakeFilterRepository.storedFilter)
    }
}