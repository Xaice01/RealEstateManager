package com.xavier_carpentier.realestatemanager.ui.setting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.xavier_carpentier.realestatemanager.domain.agent.GetAgentsUseCase
import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import com.xavier_carpentier.realestatemanager.domain.current_setting.GetCurrentAgentUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.GetCurrentDollarRateUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.GetSelectedCurrencyUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.SetCurrentAgentUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.SetCurrentDollarRateUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.SetSelectedCurrencyUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.model.CurrencyType
import com.xavier_carpentier.realestatemanager.ui.mapper.AgentDomainToUiMapper
import com.xavier_carpentier.realestatemanager.ui.mapper.CurrencyDomainToUiMapper
import com.xavier_carpentier.realestatemanager.ui.model.AgentUi
import com.xavier_carpentier.realestatemanager.ui.model.CurrencyUi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private val getAgentsUseCase: GetAgentsUseCase = mockk()
    private val getCurrentAgentUseCase: GetCurrentAgentUseCase = mockk()
    private val getSelectedCurrencyUseCase: GetSelectedCurrencyUseCase = mockk()
    private val getCurrentDollarRateUseCase: GetCurrentDollarRateUseCase = mockk()
    private val setCurrentAgentUseCase: SetCurrentAgentUseCase = mockk()
    private val setSelectedCurrencyUseCase: SetSelectedCurrencyUseCase = mockk()
    private val setCurrentDollarRateUseCase: SetCurrentDollarRateUseCase = mockk()
    private val agentDomainToUiMapper: AgentDomainToUiMapper = mockk()
    private val currencyDomainToUiMapper: CurrencyDomainToUiMapper = mockk()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var viewModel: SettingViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        coEvery { getAgentsUseCase.invoke() } returns listOf(
            AgentDomain(0, "John Smith"),
            AgentDomain(1, "Jane Smith"),
            AgentDomain(2, "John Doe"),
            AgentDomain(3, "Jane Doe"),
            AgentDomain(4, "John Wayne"),
            AgentDomain(5, "Jane Wayne")
        )

        coEvery { getCurrentAgentUseCase.invoke() } returns MutableStateFlow(AgentDomain(1, "Jane Smith"))
        coEvery { getSelectedCurrencyUseCase.invoke() } returns MutableStateFlow(CurrencyType.DOLLAR)
        coEvery { getCurrentDollarRateUseCase.invoke() } returns MutableStateFlow(0.91f)

        justRun { setCurrentAgentUseCase.invoke(any()) }
        justRun { setSelectedCurrencyUseCase.invoke(any()) }
        justRun { setCurrentDollarRateUseCase.invoke(any()) }

        every { agentDomainToUiMapper.mapToUi(any()) } answers {
            val domain = firstArg<AgentDomain>()
            AgentUi(domain.id, domain.agentName)
        }

        every { agentDomainToUiMapper.mapToDomain(any()) } answers {
            val agentUi = firstArg<AgentUi>()
            AgentDomain(agentUi.first, agentUi.second)
        }

        every { currencyDomainToUiMapper.mapToUi(any()) } answers {
            val currencyType = firstArg<CurrencyType>()
            when (currencyType) {
                CurrencyType.EURO -> CurrencyUi(displayName = "Euro", symbol = "€")
                CurrencyType.DOLLAR -> CurrencyUi(displayName = "Dollar", symbol = "$")
            }
        }

        every { currencyDomainToUiMapper.mapToDomain(any()) } answers {
            val currencyUi = firstArg<CurrencyUi>()
            when (currencyUi.displayName) {
                "Euro" -> CurrencyType.EURO
                "Dollar" -> CurrencyType.DOLLAR
                else -> throw IllegalArgumentException("Unknown currency: ${currencyUi.displayName}")
            }
        }


        // When
        viewModel = SettingViewModel(
            getAgentsUseCase,
            getCurrentAgentUseCase,
            getSelectedCurrencyUseCase,
            getCurrentDollarRateUseCase,
            setCurrentAgentUseCase,
            setSelectedCurrencyUseCase,
            setCurrentDollarRateUseCase,
            agentDomainToUiMapper,
            currencyDomainToUiMapper
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `currentAgent state flow should emit mapped AgentUi when current agent is available`() = testScope.runTest {
        // When
        val job = launch { viewModel.currentAgent.collect { } }
        advanceUntilIdle()

        val currentAgentUi = viewModel.currentAgent.value

        // Then
        assertEquals(AgentUi(1, "Jane Smith"), currentAgentUi)

        job.cancel()

    }

    @Test
    fun `agentsStateFlow should emit list of AgentUi mapped from getAgentsUseCase`() = testScope.runTest {
        // When
        val agentsUi = viewModel.agentsStateFlow.value
        // Then
        val expectedAgentsUi = listOf(
            AgentUi(0, "John Smith"),
            AgentUi(1, "Jane Smith"),
            AgentUi(2, "John Doe"),
            AgentUi(3, "Jane Doe"),
            AgentUi(4, "John Wayne"),
            AgentUi(5, "Jane Wayne")
        )
        assertEquals(expectedAgentsUi, agentsUi)
    }

    @Test
    fun `selectedCurrency state flow should emit mapped CurrencyUi when currency is available`() = testScope.runTest {
        // When
        val job = launch { viewModel.selectedCurrency.collect { } }
        advanceUntilIdle()

        val selectedCurrencyUi = viewModel.selectedCurrency.value
        // Then
        assertEquals(CurrencyUi("Dollar", symbol = "$"), selectedCurrencyUi)
        job.cancel()
    }

    @Test
    fun `currentDollarRate state flow should emit the current dollar rate`() = testScope.runTest {

        // When
        val job = launch { viewModel.currentDollarRate.collect { } }
        advanceUntilIdle()

        val dollarRate = viewModel.currentDollarRate.value
        // Then
        assertEquals(0.91f, dollarRate)

        job.cancel()
    }

    @Test
    fun `updateAgent should call setCurrentAgentUseCase with mapped AgentDomain`() = testScope.runTest {
        // Given
        val inputAgentUi = AgentUi(0, "John Smith")
        val expectedAgentDomain = AgentDomain(0, "John Smith")

        // When
        viewModel.updateAgent(inputAgentUi)
        advanceUntilIdle()
        // Then
        coVerify { setCurrentAgentUseCase.invoke(expectedAgentDomain) }
    }

    @Test
    fun `updateCurrency should call setSelectedCurrencyUseCase with mapped CurrencyType`() = testScope.runTest {
        // Given
        val inputCurrencyUi = CurrencyUi(displayName = "Dollar", symbol = "$")
        val expectedCurrencyType = CurrencyType.DOLLAR
        // When
        viewModel.updateCurrency(inputCurrencyUi)
        advanceUntilIdle()
        // Then
        coVerify { setSelectedCurrencyUseCase.invoke(expectedCurrencyType) }
    }

    @Test
    fun `updateDollarRate should call setCurrentDollarRateUseCase with provided rate`() = testScope.runTest {
        // Given
        val newRate = 0.95f
        // When
        viewModel.updateDollarRate(newRate)
        advanceUntilIdle()
        // Then
        coVerify { setCurrentDollarRateUseCase.invoke(newRate) }
    }
}