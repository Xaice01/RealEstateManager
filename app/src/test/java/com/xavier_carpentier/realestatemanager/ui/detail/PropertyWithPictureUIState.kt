package com.xavier_carpentier.realestatemanager.ui.detail

import com.xavier_carpentier.realestatemanager.domain.current_property.GetCurrentPropertyIdUseCase
import com.xavier_carpentier.realestatemanager.domain.picture.model.PictureDomain
import com.xavier_carpentier.realestatemanager.domain.property.GetPropertyWithPictureUseCase
import com.xavier_carpentier.realestatemanager.domain.property.GetPropertyWithPictureUseCaseResult
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyDomain
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain
import com.xavier_carpentier.realestatemanager.ui.mapper.PropertyWithPictureUiMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Calendar


@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    // Mocks for dependencies
    private lateinit var getPropertyWithPictureUseCase: GetPropertyWithPictureUseCase
    private lateinit var getCurrentPropertyIdUseCase: GetCurrentPropertyIdUseCase

    // ViewModel under test
    private lateinit var viewModel: DetailViewModel

    // Dummy data for tests
    private val entryDate: Calendar = Calendar.getInstance()
    private val dummyPropertyDomain = PropertyDomain(
        id = 1,
        type = "House",
        price = 250000,
        address = "123 Main St",
        latitude = 40.0,
        longitude = 74.0,
        surface = 120,
        room = 5,
        bedroom = 3,
        description = "A nice house",
        entryDate = entryDate,
        soldDate = null,
        sold = false,
        agent = "Agent A",
        interestNearbySchool = true,
        interestNearbyShop = false,
        interestNearbyPark = true,
        interestNearbyRestaurant = false,
        interestNearbyPublicTransportation = true,
        interestNearbyPharmacy = false
    )
    private val dummyPictureDomain = PictureDomain(
        id = 1,
        propertyId = 1,
        description = "Front view",
        image = byteArrayOf(1, 2, 3)
    )
    private val dummyPropertyWithPictureDomain = PropertyWithPictureDomain(
        propertyDomain = dummyPropertyDomain,
        pictureDomains = listOf(dummyPictureDomain)
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        getPropertyWithPictureUseCase = mockk()
        getCurrentPropertyIdUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState emits Empty when current property id is null`() = testScope.runTest {
        // Given: currentPropertyId use case returns null
        val propertyIdFlow = MutableStateFlow<Int?>(null)

        coEvery { getCurrentPropertyIdUseCase.invoke() } returns propertyIdFlow


        // getPropertyWithPictureUseCase won't be called when property id is null
        getPropertyWithPictureUseCase = mockk(relaxed = true)

        val job = launch { viewModel.uiState.collect { } }

        // When: instantiate DetailViewModel
        viewModel = DetailViewModel(getPropertyWithPictureUseCase, getCurrentPropertyIdUseCase)
        advanceUntilIdle()

        // Then: uiState should emit Empty
        assertEquals(PropertyWithPictureUIState.Empty, viewModel.uiState.value)

        job.cancel()
    }

    @Test
    fun `uiState emits Empty when property id is not null but use case returns Empty`() = testScope.runTest {
        // Given: current property id is non-null (e.g., 1)
        val propertyIdFlow = MutableStateFlow<Int?>(1)
        coEvery { getCurrentPropertyIdUseCase.invoke() } returns propertyIdFlow
        // And: getPropertyWithPictureUseCase returns Empty result
        coEvery { getPropertyWithPictureUseCase.invoke(1) } returns flowOf(GetPropertyWithPictureUseCaseResult.Empty)


        // When: instantiate DetailViewModel
        viewModel = DetailViewModel(getPropertyWithPictureUseCase, getCurrentPropertyIdUseCase)
        val job = launch { viewModel.uiState.collect { } }
        advanceUntilIdle()

        // Then: uiState should emit Empty
        assertEquals(PropertyWithPictureUIState.Empty, viewModel.uiState.value)

        job.cancel()
    }

    @Test
    fun `uiState emits Success when property id is not null and use case returns Success`() = testScope.runTest {
        // Given: current property id is non-null (e.g., 1)
        val propertyIdFlow = MutableStateFlow<Int?>(1)
        coEvery { getCurrentPropertyIdUseCase.invoke() } returns propertyIdFlow
        coEvery { getPropertyWithPictureUseCase.invoke(1) } returns flowOf(GetPropertyWithPictureUseCaseResult.Success(dummyPropertyWithPictureDomain))


        // When: instantiate DetailViewModel
        viewModel = DetailViewModel(getPropertyWithPictureUseCase, getCurrentPropertyIdUseCase)
        val job = launch { viewModel.uiState.collect { } }
        advanceUntilIdle()

        // Then: uiState should emit Success with the mapped property UI
        when (val state = viewModel.uiState.value) {
            is PropertyWithPictureUIState.Success -> {
                // Using the mapper to get the expected UI model (or compute expected values)
                val expectedUi = PropertyWithPictureUiMapper.mapToUi(dummyPropertyWithPictureDomain)
                assertEquals(expectedUi, state.propertyWithPicture)
            }
            else -> throw AssertionError("Expected Success state, got $state")
        }

        job.cancel()
    }

    @Test
    fun `getMapUrl returns a valid URL containing the correct coordinates`() = testScope.runTest {
        // Given: current property id is non-null and use case returns Success with dummy property (with latitude 40.0, longitude 74.0)
        val propertyIdFlow = MutableStateFlow<Int?>(1)
        coEvery { getCurrentPropertyIdUseCase.invoke() } returns propertyIdFlow
        coEvery { getPropertyWithPictureUseCase.invoke(1) } returns flowOf(GetPropertyWithPictureUseCaseResult.Success(dummyPropertyWithPictureDomain))

        // Instantiate DetailViewModel
        viewModel = DetailViewModel(getPropertyWithPictureUseCase, getCurrentPropertyIdUseCase)
        val job = launch { viewModel.uiState.collect { } }
        advanceUntilIdle()

        // When: getMapUrl is called
        val url = viewModel.getMapUrl()

        // Then: the URL should contain "center=40.0,74.0", zoom, size and key parameters
        assertTrue(url.contains("center=40.0,74.0", ignoreCase = true))

        job.cancel()

    }
}