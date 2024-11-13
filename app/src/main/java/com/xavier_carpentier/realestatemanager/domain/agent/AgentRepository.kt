package com.xavier_carpentier.realestatemanager.domain.agent

import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain

interface AgentRepository {
    fun getAgents(): List<AgentDomain>
}