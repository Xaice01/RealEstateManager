package com.xavier_carpentier.realestatemanager.ui.main

import com.xavier_carpentier.realestatemanager.domain.filter.GetFilterIsAppliedUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    // Given
    private val mutableFilterFlow = MutableStateFlow(false)

    // Given
    private val getFilterIsAppliedUseCase: GetFilterIsAppliedUseCase = mockk()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        // Given
        every { getFilterIsAppliedUseCase.invoke() } returns mutableFilterFlow

        // When
        viewModel = MainViewModel(getFilterIsAppliedUseCase)
    }

    @Test
    fun `isFilter emits initial value false`() = runTest {
        // Then
        assertEquals(false, viewModel.isFilter.value)
    }

    @Test
    fun `isFilter updates when mutable flow changes to true`() = runTest {
        // When
        mutableFilterFlow.value = true

        // Then
        assertEquals(true, viewModel.isFilter.value)
    }
}