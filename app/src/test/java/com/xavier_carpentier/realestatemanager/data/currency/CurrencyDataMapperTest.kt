package com.xavier_carpentier.realestatemanager.data.currency

import com.xavier_carpentier.realestatemanager.domain.current_setting.model.CurrencyType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class CurrencyDataMapperTest {

    private val currencyDataMapper = CurrencyDataMapper()

    @Test
    fun `mapToDomain returns EURO when currencyCode is EUR`() {
        val entity = CurrencyEntity("EUR")
        val domain = currencyDataMapper.mapToDomain(entity)
        assertEquals(CurrencyType.EURO, domain)
    }

    @Test
    fun `mapToDomain returns DOLLAR when currencyCode is USD`() {
        val entity = CurrencyEntity("USD")
        val domain = currencyDataMapper.mapToDomain(entity)
        assertEquals(CurrencyType.DOLLAR, domain)
    }

    @Test
    fun `mapToDomain throws exception for unknown currency code`() {
        val entity = CurrencyEntity("GBP")
        val exception = assertThrows(IllegalArgumentException::class.java) {
            currencyDataMapper.mapToDomain(entity)
        }
        assertEquals("Unknown currency code", exception.message)
    }

    @Test
    fun `mapToData returns CurrencyEntity with EUR when domain is EURO`() {
        val entity = currencyDataMapper.mapToData(CurrencyType.EURO)
        assertEquals("EUR", entity.currencyCode)
    }

    @Test
    fun `mapToData returns CurrencyEntity with USD when domain is DOLLAR`() {
        val entity = currencyDataMapper.mapToData(CurrencyType.DOLLAR)
        assertEquals("USD", entity.currencyCode)
    }
}