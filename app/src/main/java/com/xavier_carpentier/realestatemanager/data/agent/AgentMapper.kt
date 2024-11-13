package com.xavier_carpentier.realestatemanager.data.agent

import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import javax.inject.Inject

class AgentMapper @Inject constructor() {

    fun mapToDomain(agent: Agent): AgentDomain {
        return AgentDomain(
            id = agent.id,
            agentName = agent.agentName
        )
    }

    fun mapListToDomain(agentList: List<Agent>): List<AgentDomain> {
        return agentList.map { mapToDomain(it) }
    }

    fun mapToData(agentDomain: AgentDomain): Agent? {
        return Agent.entries
            .find { it.id == agentDomain.id && it.agentName == agentDomain.agentName }
    }

    fun mapListToData(agentDomainList: List<AgentDomain>): List<Agent?> {
        return agentDomainList.map { mapToData(it) }
    }

}
