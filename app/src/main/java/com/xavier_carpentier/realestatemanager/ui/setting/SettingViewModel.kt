package com.xavier_carpentier.realestatemanager.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xavier_carpentier.realestatemanager.domain.agent.GetAgentsUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.GetCurrentAgentUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.GetCurrentDollarRateUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.GetSelectedCurrencyUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.SetCurrentAgentUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.SetCurrentDollarRateUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.SetSelectedCurrencyUseCase
import com.xavier_carpentier.realestatemanager.ui.mapper.AgentDomainToUiMapper
import com.xavier_carpentier.realestatemanager.ui.mapper.CurrencyDomainToUiMapper
import com.xavier_carpentier.realestatemanager.ui.model.AgentUi
import com.xavier_carpentier.realestatemanager.ui.model.CurrencyUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getAgentsUseCase: GetAgentsUseCase,
    getCurrentAgentUseCase: GetCurrentAgentUseCase,
    getSelectedCurrencyUseCase: GetSelectedCurrencyUseCase,
    getCurrentDollarRateUseCase: GetCurrentDollarRateUseCase,
    private val setCurrentAgentUseCase: SetCurrentAgentUseCase,
    private val setSelectedCurrencyUseCase: SetSelectedCurrencyUseCase,
    private val setCurrentDollarRateUseCase: SetCurrentDollarRateUseCase,
    private val agentDomainToUiMapper: AgentDomainToUiMapper,
    private val currencyDomainToUiMapper: CurrencyDomainToUiMapper
) : ViewModel() {

    // Expose agent data as StateFlow
    val currentAgent: StateFlow<AgentUi?> = getCurrentAgentUseCase()
        .map { it?.let { agentDomainToUiMapper.mapToUi(it) } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    // Expose list of agents
    val agents: StateFlow<List<AgentUi>> = flow {
        emit(getAgentsUseCase().map { agentDomainToUiMapper.mapToUi(it) })
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Expose selected currency as StateFlow
    val selectedCurrency: StateFlow<CurrencyUi?> = getSelectedCurrencyUseCase()
        .map { it?.let { currencyDomainToUiMapper.mapToUi(it) } } // Convert CurrencyType to CurrencyUi
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    // Expose dollar rate as StateFlow
    val currentDollarRate: StateFlow<Float?> = getCurrentDollarRateUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    // Functions to update data
    fun updateAgent(agentUi: AgentUi) {
        val agentDomain = agentDomainToUiMapper.mapToDomain(agentUi)
        setCurrentAgentUseCase(agentDomain)
    }

    fun updateCurrency(uiModel: CurrencyUi) {
        // Convert CurrencyUi to CurrencyType using the new mapToDomain function
        val currencyType = currencyDomainToUiMapper.mapToDomain(uiModel)
        setSelectedCurrencyUseCase(currencyType)
    }

    fun updateDollarRate(rate: Float) {
        setCurrentDollarRateUseCase(rate)
    }
}