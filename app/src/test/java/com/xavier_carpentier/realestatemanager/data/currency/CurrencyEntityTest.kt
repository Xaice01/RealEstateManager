package com.xavier_carpentier.realestatemanager.data.currency

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class CurrencyEntityTest {

    @Test
    fun `CurrencyEntity equality is based on currencyCode`() {
        val entity1 = CurrencyEntity("EUR")
        val entity2 = CurrencyEntity("EUR")
        val entity3 = CurrencyEntity("USD")
        assertEquals(entity1, entity2)
        assertNotEquals(entity1, entity3)
    }

    @Test
    fun `CurrencyEntity copy creates an equal instance`() {
        val original = CurrencyEntity("USD")
        val copy = original.copy()
        assertEquals(original, copy)
    }
}