package com.xavier_carpentier.realestatemanager.ui.mapper

import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import com.xavier_carpentier.realestatemanager.ui.model.AgentUi
import junit.framework.TestCase.assertEquals
import org.junit.Test

class AgentDomainToUiMapperTest {

    private val mapper = AgentDomainToUiMapper()

    @Test
    fun `mapToUi should convert AgentDomain to AgentUi correctly`() {
        // Given: an AgentDomain instance
        val domain = AgentDomain(id = 1, agentName = "Agent Smith")
        // When: mapping to UI model
        val ui = mapper.mapToUi(domain)
        // Then: the UI model should have the same values as the domain
        assertEquals(1, ui.first)
        assertEquals("Agent Smith", ui.second)
    }

    @Test
    fun `mapListToUi should convert list of AgentDomain to list of AgentUi correctly`() {
        // Given: a list of AgentDomain instances
        val domainList = listOf(
            AgentDomain(1, "Agent Smith"),
            AgentDomain(2, "Agent Johnson")
        )
        // When: mapping the list to UI models
        val uiList = mapper.mapListToUi(domainList)
        // Then: the resulting list should have the expected values
        assertEquals(2, uiList.size)
        assertEquals(1, uiList[0].first)
        assertEquals("Agent Smith", uiList[0].second)
        assertEquals(2, uiList[1].first)
        assertEquals("Agent Johnson", uiList[1].second)
    }

    @Test
    fun `mapToDomain should convert AgentUi to AgentDomain correctly`() {
        // Given: an AgentUi instance
        val ui: AgentUi = 1 to "Agent Smith"
        // When: mapping to domain model
        val domain = mapper.mapToDomain(ui)
        // Then: the domain model should have the same values as the UI model
        assertEquals(1, domain.id)
        assertEquals("Agent Smith", domain.agentName)
    }

    @Test
    fun `mapListToDomain should convert list of AgentUi to list of AgentDomain correctly`() {
        // Given: a list of AgentUi instances
        val uiList: List<AgentUi> = listOf(1 to "Agent Smith", 2 to "Agent Johnson")
        // When: mapping the list to domain models
        val domainList = mapper.mapListToDomain(uiList)
        // Then: the resulting list should have the expected domain values
        assertEquals(2, domainList.size)
        assertEquals(1, domainList[0].id)
        assertEquals("Agent Smith", domainList[0].agentName)
        assertEquals(2, domainList[1].id)
        assertEquals("Agent Johnson", domainList[1].agentName)
    }
}