package com.xavier_carpentier.realestatemanager.domain.permission

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

// Fake implementation of PermissionLocationRepository for testing GetPermissionLocationAsStateFlowUseCase
class FakePermissionLocationRepositoryForGet : PermissionLocationRepository {
    // //Given: A MutableStateFlow to hold the permission state, initially false
    private val _permissionFlow = MutableStateFlow(false)
    
    override fun getPermissionLocation(): StateFlow<Boolean> = _permissionFlow

    // //When: setPermissionLocation is called, update the permission state
    override fun setPermissionLocation(value: Boolean) {
        _permissionFlow.value = value
    }
}

class GetPermissionLocationAsStateFlowUseCaseTest {

    @Test
    fun `invoke returns initial permission state false`() = runTest {
        // //Given: a FakePermissionLocationRepositoryForGet with an initial permission state of false
        val fakeRepository = FakePermissionLocationRepositoryForGet()
        val useCase = GetPermissionLocationAsStateFlowUseCase(fakeRepository)

        // //When: invoking the use case to retrieve the StateFlow
        val stateFlow = useCase.invoke()

        // //Then: the stateFlow's current value should be false
        assertFalse(stateFlow.value)
    }

    @Test
    fun `invoke reflects updated permission state`() = runTest {
        // //Given: a FakePermissionLocationRepositoryForGet with an initial permission state of false
        val fakeRepository = FakePermissionLocationRepositoryForGet()
        val useCase = GetPermissionLocationAsStateFlowUseCase(fakeRepository)

        // //When: updating the repository's permission to true using setPermissionLocation
        fakeRepository.setPermissionLocation(true)

        // //Then: the use case's StateFlow should now reflect the updated value true
        val stateFlow = useCase.invoke()
        assertTrue(stateFlow.value)
    }
}