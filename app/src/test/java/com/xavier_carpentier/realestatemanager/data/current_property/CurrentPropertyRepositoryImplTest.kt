package com.xavier_carpentier.realestatemanager.data.current_property

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrentPropertyRepositoryImplTest {

    @Test
    fun `initial currentPropertyIdFlow is null`() = runTest {
        // Given
        val repository = CurrentPropertyRepositoryImpl()

        // When
        val initialValue = repository.currentPropertyIdFlow.value

        // Then
        assertEquals(null, initialValue)
    }

    @Test
    fun `setCurrentPropertyId updates currentPropertyIdFlow value`() = runTest {
        // Given
        val repository = CurrentPropertyRepositoryImpl()
        val testPropertyId = 42

        // When
        repository.setCurrentPropertyId(testPropertyId)
        val updatedValue = repository.currentPropertyIdFlow.value

        // Then
        assertEquals(testPropertyId, updatedValue)
    }
}