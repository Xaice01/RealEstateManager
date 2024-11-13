package com.xavier_carpentier.realestatemanager.domain.current_setting

import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import com.xavier_carpentier.realestatemanager.domain.current_setting.model.CurrencyType
import kotlinx.coroutines.flow.StateFlow

interface CurrentSettingRepository {
    val currentAgentFlow: StateFlow<AgentDomain?>
    fun setCurrentAgent(currentAgent: AgentDomain)

    val selectedCurrencyFlow: StateFlow<CurrencyType?>
    fun setSelectedCurrency(currencyType: CurrencyType)

    val currentDollarRate: StateFlow<Float?>
    fun setCurrentDollarRate(currentRate: Float)
}
