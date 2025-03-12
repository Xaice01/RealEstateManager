package com.xavier_carpentier.realestatemanager.domain.property_type

import com.xavier_carpentier.realestatemanager.domain.property_type.model.PropertyTypeDomain
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test

class GetPropertyTypeUseCaseTest {

    private val propertyTypeRepository: PropertyTypeRepository = mockk(relaxed = true)
    private val getPropertyTypeUseCase = GetPropertyTypeUseCase(propertyTypeRepository)

    @Test
    fun `invoke should return non-empty list of property types`() {
        // Given: A repository that returns a non-empty list of property types
        val expectedPropertyTypes = listOf(
            PropertyTypeDomain(id = 1, databaseName = "House", stringRes = 100),
            PropertyTypeDomain(id = 2, databaseName = "Apartment", stringRes = 200)
        )
        every { propertyTypeRepository.getPropertyTypes() } returns expectedPropertyTypes

        // When: The use case is invoked
        val result = getPropertyTypeUseCase.invoke()

        // Then: The result should match the expected non-empty list of property types
        assertEquals(expectedPropertyTypes, result)
    }

    @Test
    fun `invoke should return empty list when repository returns no property types`() {
        // Given: A repository that returns an empty list
        val expectedPropertyTypes = emptyList<PropertyTypeDomain>()
        every { propertyTypeRepository.getPropertyTypes() } returns expectedPropertyTypes

        // When: The use case is invoked
        val result = getPropertyTypeUseCase.invoke()

        // Then: The result should be an empty list
        assertEquals(expectedPropertyTypes, result)
    }
}