package com.xavier_carpentier.realestatemanager.data.current_setting

import com.xavier_carpentier.realestatemanager.data.agent.Agent
import com.xavier_carpentier.realestatemanager.data.agent.AgentMapper
import com.xavier_carpentier.realestatemanager.data.currency.CurrencyDataMapper
import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import com.xavier_carpentier.realestatemanager.domain.current_setting.model.CurrencyType
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
class CurrentSettingRepositoryImplTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: CurrentSettingRepositoryImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = CurrentSettingRepositoryImpl(AgentMapper(), CurrencyDataMapper(), testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    //private val repository = CurrentSettingRepositoryImpl(AgentMapper(), CurrencyDataMapper())

    @Test
    fun `test initial currentAgentFlow value`() = runTest {
        // Given
        // When
        val initialAgentDomain = repository.currentAgentFlow.value

        // Then
        val expectedAgentDomain = AgentDomain(Agent.JOHN_SMITH.id, Agent.JOHN_SMITH.agentName)
        assertEquals(expectedAgentDomain, initialAgentDomain)
    }

    @Test
    fun `test setCurrentAgent updates currentAgentFlow value`() = runTest {
        // Given
        val newAgentDomain = AgentDomain(Agent.JANE_SMITH.id, Agent.JANE_SMITH.agentName)
        val job = launch { repository.currentAgentFlow.collect { } }

        // When
        repository.setCurrentAgent(newAgentDomain)
        advanceUntilIdle()

        // Then
        assertEquals(newAgentDomain, repository.currentAgentFlow.value)
        job.cancel()
    }

    @Test
    fun `test initial selectedCurrencyFlow value`() = runTest {
        // Given
        // When
        val initialCurrencyType = repository.selectedCurrencyFlow.value

        // Then
        assertEquals(CurrencyType.DOLLAR, initialCurrencyType)
    }

    @Test
    fun `test setSelectedCurrency updates selectedCurrencyFlow value`() = runTest {
        // Given
        val newCurrencyType = CurrencyType.EURO
        val job = launch { repository.selectedCurrencyFlow.collect { } }

        // When
        repository.setSelectedCurrency(newCurrencyType)
        advanceUntilIdle()

        val updatedCurrencyType = repository.selectedCurrencyFlow.value


        // Then
        assertEquals(newCurrencyType, updatedCurrencyType)

        job.cancel()
    }

    @Test
    fun `test initial currentDollarRate value`() = runTest {
        // Given
        // When
        val initialDollarRate = repository.currentDollarRate.value

        // Then
        assertEquals(0.94f, initialDollarRate)
    }

    @Test
    fun `test setCurrentDollarRate updates currentDollarRate value`() = runTest {
        // Given
        val newDollarRate = 1.0f

        // When
        repository.setCurrentDollarRate(newDollarRate)
        val updatedDollarRate = repository.currentDollarRate.value

        // Then
        assertEquals(newDollarRate, updatedDollarRate)
    }
}