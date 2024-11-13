package com.xavier_carpentier.realestatemanager.domain.agent

import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import javax.inject.Inject

class GetAgentsUseCase @Inject constructor(private val agentRepository :AgentRepository) {
    operator fun invoke():List<AgentDomain> {
        return agentRepository.getAgents()

    }
}
