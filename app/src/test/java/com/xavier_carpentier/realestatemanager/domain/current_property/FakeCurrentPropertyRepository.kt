package com.xavier_carpentier.realestatemanager.domain.current_property

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

// Fake implementation of CurrentPropertyRepository for testing purposes
class FakeCurrentPropertyRepository : CurrentPropertyRepository {
    // Given: a MutableStateFlow to hold the current property id (initially null)
    private val _currentPropertyIdFlow = MutableStateFlow<Int?>(null)
    override val currentPropertyIdFlow: StateFlow<Int?> get() = _currentPropertyIdFlow

    // When: setCurrentPropertyId is called, update the flow value
    override fun setCurrentPropertyId(currentId: Int) {
        _currentPropertyIdFlow.value = currentId
    }
}

class GetCurrentPropertyIdUseCaseTest {

    private val fakeRepository = FakeCurrentPropertyRepository()
    private val getCurrentPropertyIdUseCase = GetCurrentPropertyIdUseCase(fakeRepository)

    @Test
    fun `invoke returns current property id flow`() = runTest {
        // Given: a FakeCurrentPropertyRepository with an initial value of null
        // When: invoking GetCurrentPropertyIdUseCase to retrieve the StateFlow
        val flow = getCurrentPropertyIdUseCase.invoke()
        // Then: the flow's value should be null initially
        assertEquals(null, flow.value)

        // Given: a new current property id is set in the repository
        fakeRepository.setCurrentPropertyId(123)
        // When: reading the value from the flow
        // Then: the flow's value should now be 123
        assertEquals(123, flow.value)
    }
}

class SetCurrentPropertyUseCaseTest {

    private val fakeRepository = FakeCurrentPropertyRepository()
    private val setCurrentPropertyUseCase = SetCurrentPropertyUseCase(fakeRepository)

    @Test
    fun `invoke sets current property id in repository`() = runTest {
        // Given: a FakeCurrentPropertyRepository with an initial value of null
        // When: invoking SetCurrentPropertyUseCase with id 456
        setCurrentPropertyUseCase.invoke(456)
        // Then: the repository's currentPropertyIdFlow value should be updated to 456
        assertEquals(456, fakeRepository.currentPropertyIdFlow.value)
    }
}