package com.xavier_carpentier.realestatemanager.ui.mapper

import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import com.xavier_carpentier.realestatemanager.ui.model.AgentUi
import javax.inject.Inject

class AgentDomainToUiMapper @Inject constructor() {

    fun mapToUi(domain: AgentDomain): AgentUi {
        return domain.id to domain.agentName
    }

    fun mapListToUi(domainList: List<AgentDomain>): List<AgentUi> {
        return domainList.map { mapToUi(it) }
    }

    fun mapToDomain(ui: AgentUi): AgentDomain {
        return AgentDomain(id = ui.first, agentName = ui.second)
    }

    fun mapListToDomain(uiList: List<AgentUi>): List<AgentDomain> {
        return uiList.map { mapToDomain(it) }
    }
}