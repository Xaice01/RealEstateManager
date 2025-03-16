package com.xavier_carpentier.realestatemanager.ui.ListProperty

import com.xavier_carpentier.realestatemanager.domain.current_property.SetCurrentPropertyUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.GetSelectedCurrencyUseCase
import com.xavier_carpentier.realestatemanager.domain.picture.model.PictureDomain
import com.xavier_carpentier.realestatemanager.domain.property.GetAllPropertyFilterAsFlowUseCase
import com.xavier_carpentier.realestatemanager.domain.property.GetOnPropertyFilterUseCaseResult
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyDomain
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain
import com.xavier_carpentier.realestatemanager.ui.listProperty.ListPropertyViewModel
import com.xavier_carpentier.realestatemanager.ui.mapper.CurrencyDomainToUiMapper
import com.xavier_carpentier.realestatemanager.ui.mapper.PropertyWithPictureUiMapper
import com.xavier_carpentier.realestatemanager.ui.model.CurrencyUi
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
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
import org.junit.Before
import org.junit.Test
import java.util.Calendar
import com.xavier_carpentier.realestatemanager.ui.listProperty.ListPropertyUIState.Empty as UIEmpty
import com.xavier_carpentier.realestatemanager.ui.listProperty.ListPropertyUIState.Success as UISuccess

@OptIn(ExperimentalCoroutinesApi::class)
class ListPropertyViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    // Mocks pour les dépendances
    private lateinit var getAllPropertyFilterAsFlowUseCase: GetAllPropertyFilterAsFlowUseCase
    private lateinit var setCurrentPropertyUseCase: SetCurrentPropertyUseCase
    private lateinit var getSelectedCurrencyUseCase: GetSelectedCurrencyUseCase
    private lateinit var currencyMapper: CurrencyDomainToUiMapper

    private lateinit var viewModel: ListPropertyViewModel

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


        every { getSelectedCurrencyUseCase.invoke() } returns MutableStateFlow(com.xavier_carpentier.realestatemanager.domain.current_setting.model.CurrencyType.DOLLAR)
        every { currencyMapper.mapToUi(com.xavier_carpentier.realestatemanager.domain.current_setting.model.CurrencyType.DOLLAR) } returns CurrencyUi("Dollar", "$")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `uiState emits Empty state when property flow is empty`() = testScope.runTest {
        // Given
        every { getAllPropertyFilterAsFlowUseCase.invoke() } returns flowOf(GetOnPropertyFilterUseCaseResult.Empty)
        // When
        viewModel = ListPropertyViewModel(getAllPropertyFilterAsFlowUseCase, setCurrentPropertyUseCase, getSelectedCurrencyUseCase, currencyMapper)


        val job = launch { viewModel.uiState.collect { } }
        advanceUntilIdle()

        advanceUntilIdle()
        // Then
        assertEquals(UIEmpty, viewModel.uiState.value)

        job.cancel()
    }

    @Test
    fun `uiState emits Success state when property flow returns properties`() = testScope.runTest {
        // Given
        val dummyDomainList = listDomain // On se contente d'une liste non vide.

        val successResult = GetOnPropertyFilterUseCaseResult.Success(dummyDomainList)
        every { getAllPropertyFilterAsFlowUseCase.invoke() } returns flowOf(successResult)

        val dummyUiList = listUi

        // When
        viewModel = ListPropertyViewModel(getAllPropertyFilterAsFlowUseCase, setCurrentPropertyUseCase, getSelectedCurrencyUseCase, currencyMapper)

        val job = launch { viewModel.uiState.collect { } }
        advanceUntilIdle()
        // Then
        val expectedState = UISuccess(dummyUiList, CurrencyUi("Dollar", "$"))
        assertEquals(expectedState, viewModel.uiState.value)

        job.cancel()
    }

    @Test
    fun `onSelectProperty should call setCurrentPropertyUseCase with given id`() = testScope.runTest {
        // Given
        val propertyId = 42
        // When
        viewModel = ListPropertyViewModel(getAllPropertyFilterAsFlowUseCase, setCurrentPropertyUseCase, getSelectedCurrencyUseCase, currencyMapper)
        advanceUntilIdle()
        // When
        viewModel.onSelectProperty(propertyId)
        // Then
        coVerify { setCurrentPropertyUseCase.invoke(propertyId) }
    }
}