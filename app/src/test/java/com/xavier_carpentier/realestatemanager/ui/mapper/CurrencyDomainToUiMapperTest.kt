package com.xavier_carpentier.realestatemanager.ui.mapper

import com.xavier_carpentier.realestatemanager.domain.current_setting.model.CurrencyType
import com.xavier_carpentier.realestatemanager.ui.model.CurrencyUi
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CurrencyDomainToUiMapperTest {

    private val mapper = CurrencyDomainToUiMapper()

    @Test
    fun `mapToUi should return Euro for CurrencyType EURO`() {
        // Given: CurrencyType.EURO
        // When: mapping to UI model
        val ui = mapper.mapToUi(CurrencyType.EURO)
        // Then: the UI model should represent Euro correctly
        assertEquals("Euro", ui.displayName)
        assertEquals("€", ui.symbol)
    }

    @Test
    fun `mapToUi should return Dollar for CurrencyType DOLLAR`() {
        // Given: CurrencyType.DOLLAR
        // When: mapping to UI model
        val ui = mapper.mapToUi(CurrencyType.DOLLAR)
        // Then: the UI model should represent Dollar correctly
        assertEquals("Dollar", ui.displayName)
        assertEquals("$", ui.symbol)
    }

    @Test
    fun `mapToDomain should return CurrencyType EURO when UI displays Euro`() {
        // Given: a CurrencyUi representing Euro
        val ui = CurrencyUi(displayName = "Euro", symbol = "€")
        // When: mapping to domain model
        val domain = mapper.mapToDomain(ui)
        // Then: the domain model should be CurrencyType.EURO
        assertEquals(CurrencyType.EURO, domain)
    }

    @Test
    fun `mapToDomain should return CurrencyType DOLLAR when UI displays Dollar`() {
        // Given: a CurrencyUi representing Dollar
        val ui = CurrencyUi(displayName = "Dollar", symbol = "$")
        // When: mapping to domain model
        val domain = mapper.mapToDomain(ui)
        // Then: the domain model should be CurrencyType.DOLLAR
        assertEquals(CurrencyType.DOLLAR, domain)
    }
}