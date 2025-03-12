package com.xavier_carpentier.realestatemanager.data.agent

import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import org.junit.Assert.assertEquals
import org.junit.Test

class AgentRepositoryImplTest {

    private val agentRepository = AgentRepositoryImpl(AgentMapper())

    @Test
    fun testGetAgents() {
        val agentsDomain: List<AgentDomain> = agentRepository.getAgents()
        // La liste attendue est dans l'ordre défini dans le repository
        val expectedAgents = listOf(
            Agent.JOHN_SMITH,
            Agent.JANE_SMITH,
            Agent.JOHN_DOE,
            Agent.JANE_DOE,
            Agent.JOHN_WAYNE,
            Agent.JANE_WAYNE
        )
        assertEquals(expectedAgents.size, agentsDomain.size)
        expectedAgents.forEachIndexed { index, expectedAgent ->
            val actualDomain = agentsDomain[index]
            assertEquals(expectedAgent.id, actualDomain.id)
            assertEquals(expectedAgent.agentName, actualDomain.agentName)
        }
    }
}