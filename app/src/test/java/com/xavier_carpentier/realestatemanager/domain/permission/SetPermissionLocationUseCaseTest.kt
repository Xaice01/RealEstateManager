package com.xavier_carpentier.realestatemanager.domain.permission

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

// Fake implementation of PermissionLocationRepository for testing purposes
class FakePermissionLocationRepository : PermissionLocationRepository {
    // //Given: A MutableStateFlow to hold the permission state, initially false
    private val _permissionFlow = MutableStateFlow(false)
    
    override fun getPermissionLocation(): StateFlow<Boolean> = _permissionFlow

    // //When: setPermissionLocation is called, update the permission state
    override fun setPermissionLocation(value: Boolean) {
        _permissionFlow.value = value
    }
}

class SetPermissionLocationUseCaseTest {

    @Test
    fun `invoke sets permission to true`() = runTest {
        // //Given: a FakePermissionLocationRepository with an initial permission state of false
        val fakeRepository = FakePermissionLocationRepository()
        val useCase = SetPermissionLocationUseCase(fakeRepository)

        // //When: invoking the use case with value true
        useCase.invoke(true)

        // //Then: the repository's permission state should be true
        assertTrue(fakeRepository.getPermissionLocation().value)
    }

    @Test
    fun `invoke sets permission to false`() = runTest {
        // //Given: a FakePermissionLocationRepository with an initial permission state of false
        val fakeRepository = FakePermissionLocationRepository()
        val useCase = SetPermissionLocationUseCase(fakeRepository)

        // //When: invoking the use case with value false
        useCase.invoke(false)

        // //Then: the repository's permission state should be false
        assertFalse(fakeRepository.getPermissionLocation().value)
    }
}