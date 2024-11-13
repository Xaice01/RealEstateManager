package com.xavier_carpentier.realestatemanager.ui.mapper

import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import com.xavier_carpentier.realestatemanager.ui.model.AgentUi
import javax.inject.Inject

class AgentDomainToUiMapper @Inject constructor() {

    // Mapper AgentDomain vers AgentUi
    fun mapToUi(domain: AgentDomain): AgentUi {
        return domain.id to domain.agentName
    }

    // Mapper une liste de AgentDomain vers une liste de AgentUi
    fun mapListToUi(domainList: List<AgentDomain>): List<AgentUi> {
        return domainList.map { mapToUi(it) }
    }

    // Mapper AgentUi vers AgentDomain
    fun mapToDomain(ui: AgentUi): AgentDomain {
        return AgentDomain(id = ui.first, agentName = ui.second)
    }

    // Mapper une liste de AgentUi vers une liste de AgentDomain
    fun mapListToDomain(uiList: List<AgentUi>): List<AgentDomain> {
        return uiList.map { mapToDomain(it) }
    }
}