package com.xavier_carpentier.realestatemanager.data.permission

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PermissionLocationRepositoryImplTest {

    private lateinit var repository: PermissionLocationRepositoryImpl

    @Before
    fun setup() {
        // Given
        repository = PermissionLocationRepositoryImpl()
    }

    @Test
    fun `getPermissionLocation returns initial value false`() = runTest {

        // When
        val initialPermission = repository.getPermissionLocation().first()
        // Then
        assertFalse(initialPermission)
    }

    @Test
    fun `setPermissionLocation updates value to true`() = runTest {

        // When
        repository.setPermissionLocation(true)
        val updatedPermission = repository.getPermissionLocation().first()
        // Then
        assertTrue(updatedPermission)
    }

    @Test
    fun `setPermissionLocation updates value to false after being true`() = runTest {
        // Given
        repository.setPermissionLocation(true)
        // When
        repository.setPermissionLocation(false)
        val updatedPermission = repository.getPermissionLocation().first()
        // Then
        assertFalse(updatedPermission)
    }
}