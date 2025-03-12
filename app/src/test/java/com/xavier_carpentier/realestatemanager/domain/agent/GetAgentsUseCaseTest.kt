package com.xavier_carpentier.realestatemanager.domain.agent

import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import org.junit.Assert.assertEquals
import org.junit.Test

class GetAgentsUseCaseTest {

    // Given: a fake implementation of AgentRepository returning a fixed list of agents
    private val fakeAgentRepository = object : AgentRepository {
        override fun getAgents(): List<AgentDomain> {
            return listOf(
                AgentDomain(id = 1, agentName = "Agent Smith"),
                AgentDomain(id = 2, agentName = "Agent Johnson"),
                AgentDomain(id = 3, agentName = "Agent Brown")
            )
        }
    }

    // Given: an instance of GetAgentsUseCase using the fake repository
    private val getAgentsUseCase = GetAgentsUseCase(fakeAgentRepository)

    @Test
    fun `invoke returns list of agents from repository`() {
        // When: invoking the use case
        val agents = getAgentsUseCase.invoke()
        
        // Then: the returned list should match the expected list from the fake repository
        val expectedAgents = listOf(
            AgentDomain(id = 1, agentName = "Agent Smith"),
            AgentDomain(id = 2, agentName = "Agent Johnson"),
            AgentDomain(id = 3, agentName = "Agent Brown")
        )
        assertEquals(expectedAgents, agents)
    }
}