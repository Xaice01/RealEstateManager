package com.xavier_carpentier.realestatemanager.data.agent

import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AgentMapperTest {

    private val agentMapper = AgentMapper()

    @Test
    fun testMapToDomain() {
        val agent = Agent.JOHN_SMITH
        val domain = agentMapper.mapToDomain(agent)
        assertEquals(agent.id, domain.id)
        assertEquals(agent.agentName, domain.agentName)
    }

    @Test
    fun testMapListToDomain() {
        val agents = listOf(Agent.JOHN_SMITH, Agent.JANE_SMITH)
        val domains = agentMapper.mapListToDomain(agents)
        assertEquals(agents.size, domains.size)
        for (i in agents.indices) {
            assertEquals(agents[i].id, domains[i].id)
            assertEquals(agents[i].agentName, domains[i].agentName)
        }
    }

    @Test
    fun testMapToData_valid() {
        // On teste le mapping inverse pour un AgentDomain correspondant à un Agent existant.
        val domain = AgentDomain(id = Agent.JOHN_SMITH.id, agentName = Agent.JOHN_SMITH.agentName)
        val dataAgent = agentMapper.mapToData(domain)
        assertEquals(Agent.JOHN_SMITH, dataAgent)
    }

    @Test
    fun testMapToData_invalid() {
        // On teste le cas où aucun Agent ne correspond aux valeurs données.
        val domain = AgentDomain(id = 99, agentName = "Unknown Agent")
        val dataAgent = agentMapper.mapToData(domain)
        assertNull(dataAgent)
    }

    @Test
    fun testMapListToData() {
        val domainList = listOf(
            AgentDomain(id = Agent.JOHN_SMITH.id, agentName = Agent.JOHN_SMITH.agentName),
            AgentDomain(id = Agent.JANE_SMITH.id, agentName = Agent.JANE_SMITH.agentName)
        )
        val dataList = agentMapper.mapListToData(domainList)
        assertEquals(domainList.size, dataList.size)
        dataList.forEachIndexed { index, dataAgent ->
            // Pour les AgentDomain valides, dataAgent ne doit pas être null
            assertEquals(domainList[index].id, dataAgent?.id)
            assertEquals(domainList[index].agentName, dataAgent?.agentName)
        }
    }
}