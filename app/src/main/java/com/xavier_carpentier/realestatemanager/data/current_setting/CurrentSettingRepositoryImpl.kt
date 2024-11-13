package com.xavier_carpentier.realestatemanager.data.current_setting

import com.xavier_carpentier.realestatemanager.data.agent.Agent
import com.xavier_carpentier.realestatemanager.data.agent.AgentMapper
import com.xavier_carpentier.realestatemanager.data.currency.CurrencyDataMapper
import com.xavier_carpentier.realestatemanager.data.currency.CurrencyEntity
import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import com.xavier_carpentier.realestatemanager.domain.current_setting.CurrentSettingRepository
import com.xavier_carpentier.realestatemanager.domain.current_setting.model.CurrencyType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentSettingRepositoryImpl @Inject constructor(
    private val agentMapper: AgentMapper
    ,private val currencyMapper: CurrencyDataMapper
) :CurrentSettingRepository {
    private val currentAgentMutableStateFlow = MutableStateFlow<Agent?>(Agent.JOHN_SMITH)
    override val currentAgentFlow: StateFlow<AgentDomain?> = currentAgentMutableStateFlow
        .map { agent -> agent?.let { agentMapper.mapToDomain(it) } }
        .stateIn(
            scope = CoroutineScope(Dispatchers.IO),
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = agentMapper.mapToDomain(Agent.JOHN_SMITH)
        )
    override fun setCurrentAgent(currentAgent: AgentDomain){
        currentAgentMutableStateFlow.value=agentMapper.mapToData(currentAgent)
    }


    private val selectedCurrencyMutableStateFlow = MutableStateFlow<CurrencyEntity?>(CurrencyEntity("USD"))
    override val selectedCurrencyFlow: StateFlow<CurrencyType?> = selectedCurrencyMutableStateFlow
        .map { currencyEntity -> currencyEntity?.let { currencyMapper.mapToDomain(it) } }
        .stateIn(
            scope = CoroutineScope(Dispatchers.IO),
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = currencyMapper.mapToDomain(CurrencyEntity("USD"))
        )

    override fun setSelectedCurrency(currencyType: CurrencyType) {
        val currencyEntity = currencyMapper.mapToData(currencyType)
        selectedCurrencyMutableStateFlow.value = currencyEntity
    }

    private val currentDollarRateStateFlow = MutableStateFlow<Float?>(0.94f)
    override val currentDollarRate: StateFlow<Float?> = currentDollarRateStateFlow.asStateFlow()
    override fun setCurrentDollarRate(currentRate: Float){
        currentDollarRateStateFlow.value = currentRate
    }

}