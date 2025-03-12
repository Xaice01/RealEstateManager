package com.xavier_carpentier.realestatemanager.domain.current_setting

import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import com.xavier_carpentier.realestatemanager.domain.current_setting.model.CurrencyType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

// Fake implementation of CurrentSettingRepository for testing purposes
class FakeCurrentSettingRepository : CurrentSettingRepository {
    // Given: MutableStateFlow for currentAgent, initially null
    private val _currentAgentFlow = MutableStateFlow<AgentDomain?>(null)
    override val currentAgentFlow: StateFlow<AgentDomain?> get() = _currentAgentFlow

    // Given: MutableStateFlow for selectedCurrency, initially null
    private val _selectedCurrencyFlow = MutableStateFlow<CurrencyType?>(null)
    override val selectedCurrencyFlow: StateFlow<CurrencyType?> get() = _selectedCurrencyFlow

    // Given: MutableStateFlow for currentDollarRate, initially null
    private val _currentDollarRate = MutableStateFlow<Float?>(null)
    override val currentDollarRate: StateFlow<Float?> get() = _currentDollarRate

    // When: setCurrentAgent is called, update the current agent flow
    override fun setCurrentAgent(currentAgent: AgentDomain) {
        _currentAgentFlow.value = currentAgent
    }

    // When: setSelectedCurrency is called, update the selected currency flow
    override fun setSelectedCurrency(currencyType: CurrencyType) {
        _selectedCurrencyFlow.value = currencyType
    }

    // When: setCurrentDollarRate is called, update the current dollar rate flow
    override fun setCurrentDollarRate(currentRate: Float) {
        _currentDollarRate.value = currentRate
    }
}

//------------------------- GetCurrentAgentUseCase -------------------------

class GetCurrentAgentUseCaseTest {

    private val fakeRepository = FakeCurrentSettingRepository()
    private val getCurrentAgentUseCase = GetCurrentAgentUseCase(fakeRepository)

    @Test
    fun `invoke returns current agent flow`() = runTest {
        // Given: a FakeCurrentSettingRepository with currentAgentFlow initially null
        val agentFlow = getCurrentAgentUseCase.invoke()

        // Then: the initial value of the agent flow should be null
        assertEquals(null, agentFlow.value)

        // Given: set a test agent in the fake repository
        val testAgent = AgentDomain(id = 1, agentName = "Test Agent")
        fakeRepository.setCurrentAgent(testAgent)

        // When: retrieving the agent flow value after update
        // Then: the flow's value should now be equal to the test agent
        assertEquals(testAgent, agentFlow.value)
    }
}

//------------------------- SetCurrentAgentUseCase -------------------------

class SetCurrentAgentUseCaseTest {

    private val fakeRepository = FakeCurrentSettingRepository()
    private val setCurrentAgentUseCase = SetCurrentAgentUseCase(fakeRepository)

    @Test
    fun `invoke sets current agent in repository`() = runTest {
        // Given: a FakeCurrentSettingRepository with currentAgentFlow initially null

        // When: invoking SetCurrentAgentUseCase with a test agent
        val testAgent = AgentDomain(id = 2, agentName = "Agent 2")
        setCurrentAgentUseCase.invoke(testAgent)

        // Then: the fake repository's currentAgentFlow value should be updated to the test agent
        assertEquals(testAgent, fakeRepository.currentAgentFlow.value)
    }
}

//------------------------- GetCurrentDollarRateUseCase -------------------------

class GetCurrentDollarRateUseCaseTest {

    private val fakeRepository = FakeCurrentSettingRepository()
    private val getCurrentDollarRateUseCase = GetCurrentDollarRateUseCase(fakeRepository)

    @Test
    fun `invoke returns current dollar rate flow`() = runTest {
        // Given: a FakeCurrentSettingRepository with currentDollarRate initially null
        val rateFlow = getCurrentDollarRateUseCase.invoke()

        // Then: the initial value of the dollar rate flow should be null
        assertEquals(null, rateFlow.value)

        // Given: update the fake repository's current dollar rate to 1.25f
        fakeRepository.setCurrentDollarRate(1.25f)

        // When: reading the updated value from the dollar rate flow
        // Then: the flow's value should now be 1.25f
        assertEquals(1.25f, rateFlow.value)
    }
}

//------------------------- SetCurrentDollarRateUseCase -------------------------

class SetCurrentDollarRateUseCaseTest {

    private val fakeRepository = FakeCurrentSettingRepository()
    private val setCurrentDollarRateUseCase = SetCurrentDollarRateUseCase(fakeRepository)

    @Test
    fun `invoke sets current dollar rate in repository`() = runTest {
        // Given: a FakeCurrentSettingRepository with currentDollarRate initially null

        // When: invoking SetCurrentDollarRateUseCase with a new dollar rate of 1.50f
        setCurrentDollarRateUseCase.invoke(1.50f)

        // Then: the fake repository's currentDollarRate should be updated to 1.50f
        assertEquals(1.50f, fakeRepository.currentDollarRate.value)
    }
}

//------------------------- GetSelectedCurrencyUseCase -------------------------

class GetSelectedCurrencyUseCaseTest {

    private val fakeRepository = FakeCurrentSettingRepository()
    private val getSelectedCurrencyUseCase = GetSelectedCurrencyUseCase(fakeRepository)

    @Test
    fun `invoke returns selected currency flow`() = runTest {
        // Given: a FakeCurrentSettingRepository with selectedCurrencyFlow initially null
        val currencyFlow = getSelectedCurrencyUseCase.invoke()

        // Then: the initial value of the selected currency flow should be null
        assertEquals(null, currencyFlow.value)

        // Given: update the fake repository's selected currency to DOLLAR
        fakeRepository.setSelectedCurrency(CurrencyType.DOLLAR)

        // When: retrieving the updated selected currency value from the flow
        // Then: the flow's value should now be CurrencyType.DOLLAR
        assertEquals(CurrencyType.DOLLAR, currencyFlow.value)
    }
}

//------------------------- SetSelectedCurrencyUseCase -------------------------

class SetSelectedCurrencyUseCaseTest {

    private val fakeRepository = FakeCurrentSettingRepository()
    private val setSelectedCurrencyUseCase = SetSelectedCurrencyUseCase(fakeRepository)

    @Test
    fun `invoke sets selected currency in repository`() = runTest {
        // Given: a FakeCurrentSettingRepository with selectedCurrencyFlow initially null

        // When: invoking SetSelectedCurrencyUseCase with CurrencyType.EURO
        setSelectedCurrencyUseCase.invoke(CurrencyType.EURO)

        // Then: the fake repository's selectedCurrencyFlow should be updated to CurrencyType.EURO
        assertEquals(CurrencyType.EURO, fakeRepository.selectedCurrencyFlow.value)
    }
}