package com.xavier_carpentier.realestatemanager.data.agent

import com.xavier_carpentier.realestatemanager.domain.agent.AgentRepository
import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import javax.inject.Inject

class AgentRepositoryImpl @Inject constructor( private val agentMapper: AgentMapper) :AgentRepository {
    private val agents: List<Agent> = listOf(
        Agent.JOHN_SMITH,
        Agent.JANE_SMITH,
        Agent.JOHN_DOE,
        Agent.JANE_DOE,
        Agent.JOHN_WAYNE,
        Agent.JANE_WAYNE
    )
    override fun getAgents() : List<AgentDomain> = agentMapper.mapListToDomain(agents)
}

enum class Agent(val id :Int, val agentName: String){
    JOHN_SMITH(0, "John Smith"),
    JANE_SMITH(1, "Jane Smith"),
    JOHN_DOE(2, "John Doe"),
    JANE_DOE(3, "Jane Doe"),
    JOHN_WAYNE(4, "John Wayne"),
    JANE_WAYNE(5, "Jane Wayne")
}