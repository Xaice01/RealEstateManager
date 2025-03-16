package com.xavier_carpentier.realestatemanager.ui.map

import android.location.Location
import com.xavier_carpentier.realestatemanager.domain.current_property.SetCurrentPropertyUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.GetSelectedCurrencyUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.model.CurrencyType
import com.xavier_carpentier.realestatemanager.domain.location.GetLastLocationUseCase
import com.xavier_carpentier.realestatemanager.domain.permission.GetPermissionLocationAsStateFlowUseCase
import com.xavier_carpentier.realestatemanager.domain.permission.SetPermissionLocationUseCase
import com.xavier_carpentier.realestatemanager.domain.picture.model.PictureDomain
import com.xavier_carpentier.realestatemanager.domain.property.GetAllPropertyFilterAsFlowUseCase
import com.xavier_carpentier.realestatemanager.domain.property.GetOnPropertyFilterUseCaseResult
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyDomain
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain
import com.xavier_carpentier.realestatemanager.ui.listProperty.ListPropertyUIState.Empty
import com.xavier_carpentier.realestatemanager.ui.listProperty.ListPropertyUIState.Success
import com.xavier_carpentier.realestatemanager.ui.mapper.CurrencyDomainToUiMapper
import com.xavier_carpentier.realestatemanager.ui.mapper.PropertyWithPictureUiMapper
import com.xavier_carpentier.realestatemanager.ui.model.CurrencyUi
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
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
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Calendar

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MapViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    // Mocks for dependencies
    private lateinit var getAllPropertyFilterAsFlowUseCase: GetAllPropertyFilterAsFlowUseCase
    private lateinit var setCurrentPropertyUseCase: SetCurrentPropertyUseCase
    private lateinit var getSelectedCurrencyUseCase: GetSelectedCurrencyUseCase
    private lateinit var currencyMapper: CurrencyDomainToUiMapper
    private lateinit var getPermissionLocationAsStateFlowUseCase: GetPermissionLocationAsStateFlowUseCase
    private lateinit var setPermissionLocationUseCase: SetPermissionLocationUseCase
    private lateinit var getLastLocationUseCase: GetLastLocationUseCase

    // ViewModel under test
    private lateinit var viewModel: MapViewModel

    val entryDate = Calendar.getInstance()
    val propertyDomain1 = PropertyDomain(
        id = 1,
        type = "House",
        price = 250000,
        address = "123 Main St",
        latitude = 0.0,
        longitude = 0.0,
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
    val propertyDomain2 = PropertyDomain(
        id = 2,
        type = "Apartment",
        price = 150000,
        address = "456 Market St",
        latitude = 1.0,
        longitude = 1.0,
        surface = 80,
        room = 4,
        bedroom = 2,
        description = "Modern apartment",
        entryDate = entryDate,
        soldDate = null,
        sold = false,
        agent = "Agent B",
        interestNearbySchool = false,
        interestNearbyShop = true,
        interestNearbyPark = false,
        interestNearbyRestaurant = true,
        interestNearbyPublicTransportation = false,
        interestNearbyPharmacy = true
    )
    val pictureDomain = PictureDomain(
        id = 1,
        propertyId = 1,
        description = "Front view",
        image = byteArrayOf(1, 2, 3)
    )
    val listDomain = listOf(
        PropertyWithPictureDomain(propertyDomain1, listOf(pictureDomain)),
        PropertyWithPictureDomain(propertyDomain2, listOf())
    )

    val listUi = PropertyWithPictureUiMapper.mapListToUi(listDomain)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Given
        getAllPropertyFilterAsFlowUseCase = mockk()
        setCurrentPropertyUseCase = mockk(relaxed = true)
        getSelectedCurrencyUseCase = mockk()
        currencyMapper = mockk()
        getPermissionLocationAsStateFlowUseCase = mockk()
        setPermissionLocationUseCase = mockk(relaxed = true)
        getLastLocationUseCase = mockk()


        every { getSelectedCurrencyUseCase.invoke() } returns MutableStateFlow(CurrencyType.DOLLAR)
        every { currencyMapper.mapToUi(CurrencyType.DOLLAR) } returns CurrencyUi("Dollar", "$")
        every { getAllPropertyFilterAsFlowUseCase.invoke() } returns flowOf(GetOnPropertyFilterUseCaseResult.Empty)

        val permissionLocation = MutableStateFlow(false)
        every { getPermissionLocationAsStateFlowUseCase.invoke() } returns permissionLocation
        every { setPermissionLocationUseCase.invoke(any()) } answers { permissionLocation.value = firstArg() }
        coEvery { getLastLocationUseCase.invoke() } returns Location("test").apply {
            latitude = 40.0
            longitude = 74.0
        }

        // When
        viewModel = MapViewModel(
            getAllPropertyFilterAsFlowUseCase,
            setCurrentPropertyUseCase,
            getSelectedCurrencyUseCase,
            currencyMapper,
            getPermissionLocationAsStateFlowUseCase,
            setPermissionLocationUseCase,
            getLastLocationUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState emits Empty state when property flow is empty`() = testScope.runTest {
        // Given
        every { getAllPropertyFilterAsFlowUseCase.invoke() } returns flowOf(GetOnPropertyFilterUseCaseResult.Empty)

        // When
        viewModel = MapViewModel(
            getAllPropertyFilterAsFlowUseCase,
            setCurrentPropertyUseCase,
            getSelectedCurrencyUseCase,
            currencyMapper,
            getPermissionLocationAsStateFlowUseCase,
            setPermissionLocationUseCase,
            getLastLocationUseCase
        )


        val job = launch { viewModel.uiState.collect { } }
        advanceUntilIdle()

        advanceUntilIdle()
        // Then
        Assert.assertEquals(Empty, viewModel.uiState.value)

        job.cancel()
    }

    @Test
    fun `uiState emits Success state when property flow returns properties`() = testScope.runTest {
        // Given:
        val dummyDomainList = listDomain

        val successResult = GetOnPropertyFilterUseCaseResult.Success(dummyDomainList)
        every { getAllPropertyFilterAsFlowUseCase.invoke() } returns flowOf(successResult)

        val dummyUiList = listUi


        // When
        viewModel = MapViewModel(
            getAllPropertyFilterAsFlowUseCase,
            setCurrentPropertyUseCase,
            getSelectedCurrencyUseCase,
            currencyMapper,
            getPermissionLocationAsStateFlowUseCase,
            setPermissionLocationUseCase,
            getLastLocationUseCase
        )

        val job = launch { viewModel.uiState.collect { } }
        advanceUntilIdle()
        // Then
        val expectedState = Success(dummyUiList, CurrencyUi("Dollar", "$"))
        Assert.assertEquals(expectedState, viewModel.uiState.value)

        job.cancel()
    }

    @Test
    fun `onSelectProperty should call setCurrentPropertyUseCase with given propertyId`() = testScope.runTest {
        // Given
        val propertyId = 123

        // When
        viewModel.onSelectProperty(propertyId)

        // Then
        verify { setCurrentPropertyUseCase(propertyId) }
    }

    @Test
    fun `setPermissionLocation should call setPermissionLocationUseCase with given boolean`() = testScope.runTest {
        // Given
        val permissionGranted = true

        // When
        viewModel.setPermissionLocation(permissionGranted)

        // Then
        verify { setPermissionLocationUseCase(permissionGranted) }
    }

    @Test
    fun `should update location when permission is granted and getLastLocationUseCase returns a location`() = testScope.runTest {
        //Given
        val permissionLocation = MutableStateFlow(true)
        every { getPermissionLocationAsStateFlowUseCase.invoke() } returns permissionLocation

        // When
        viewModel = MapViewModel(
            getAllPropertyFilterAsFlowUseCase,
            setCurrentPropertyUseCase,
            getSelectedCurrencyUseCase,
            currencyMapper,
            getPermissionLocationAsStateFlowUseCase,
            setPermissionLocationUseCase,
            getLastLocationUseCase
        )

        val job = launch { viewModel.location.collect { } }
        advanceUntilIdle()

        // Then
        val updatedLocation = viewModel.location.value
        assertEquals(40.0, updatedLocation.latitude)
        assertEquals(74.0, updatedLocation.longitude)

        job.cancel()
    }
}