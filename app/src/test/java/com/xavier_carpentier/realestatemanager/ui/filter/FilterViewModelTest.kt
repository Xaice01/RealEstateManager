import com.xavier_carpentier.realestatemanager.domain.filter.GetFilterPropertyWithPictureAsFLowUseCase
import com.xavier_carpentier.realestatemanager.domain.filter.GetFilterPropertyWithPictureUseCaseResult
import com.xavier_carpentier.realestatemanager.domain.filter.GetFilterPropertyWithPictureUseCaseResult.Empty
import com.xavier_carpentier.realestatemanager.domain.filter.ResetFilterPropertyWithPictureUseCase
import com.xavier_carpentier.realestatemanager.domain.filter.SetFilterPropertyWithPictureUseCase
import com.xavier_carpentier.realestatemanager.domain.filter.model.FilterDomain
import com.xavier_carpentier.realestatemanager.domain.property_type.GetPropertyTypeUseCase
import com.xavier_carpentier.realestatemanager.domain.property_type.model.PropertyTypeDomain
import com.xavier_carpentier.realestatemanager.ui.filter.FilterViewModel
import com.xavier_carpentier.realestatemanager.ui.model.FilterType
import com.xavier_carpentier.realestatemanager.ui.model.FilterUi
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import com.xavier_carpentier.realestatemanager.ui.filter.FilterUiState.Empty as UIEmpty
import com.xavier_carpentier.realestatemanager.ui.filter.FilterUiState.Success as UISuccess

@OptIn(ExperimentalCoroutinesApi::class)
class FilterViewModelTest {


    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)


    private lateinit var getFilterPropertyWithPictureAsFLowUseCase: GetFilterPropertyWithPictureAsFLowUseCase
    private lateinit var resetFilterPropertyWithPictureUseCase: ResetFilterPropertyWithPictureUseCase
    private lateinit var setFilterPropertyWithPictureUseCase: SetFilterPropertyWithPictureUseCase
    private lateinit var getPropertyTypeUseCase: GetPropertyTypeUseCase


    private lateinit var viewModel: FilterViewModel


    private val emptyFilter = FilterUi(
        emptyList(),
        0,
        0,
        0,
        0,
        false,
        false,
        false,
        false,
        false,
        false,
        false
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        // Given
        getFilterPropertyWithPictureAsFLowUseCase = mockk()
        resetFilterPropertyWithPictureUseCase = mockk(relaxed = true)
        setFilterPropertyWithPictureUseCase = mockk(relaxed = true)
        getPropertyTypeUseCase = mockk()

        // Given
        every { getPropertyTypeUseCase.invoke() } returns listOf(
            // On crée une instance concrète de PropertyTypeDomain
            PropertyTypeDomain(id = 1L, databaseName = "TestDB", stringRes = 111)
        )


        every { getFilterPropertyWithPictureAsFLowUseCase.invoke() } returns flowOf(Empty)


    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `filterUiState emits Empty state when getFilter returns Empty`() = runTest {
        // Given
        every { getFilterPropertyWithPictureAsFLowUseCase.invoke() } returns flowOf(Empty)
        // When
        viewModel = FilterViewModel(
            getFilterPropertyWithPictureAsFLowUseCase,
            resetFilterPropertyWithPictureUseCase,
            setFilterPropertyWithPictureUseCase,
            getPropertyTypeUseCase
        )

        val job = launch { viewModel.filterUiState.collect {} }
        advanceUntilIdle()
        // Then
        assertEquals(UIEmpty, viewModel.filterUiState.value)

        assertEquals(emptyFilter, viewModel.filter.value)

        job.cancel()

    }

    @Test
    fun `filterUiState emits Success state when getFilter returns Success`() = runTest {
        // Given
        val dummyFilterDomain = FilterDomain(
            types = emptyList(),
            minPrice = 1000,
            maxPrice = 5000,
            minSurface = 50,
            maxSurface = 200,
            sold = true,
            nearbySchool = true,
            nearbyShop = false,
            nearbyPark = false,
            nearbyRestaurant = false,
            nearbyPublicTransportation = true,
            nearbyPharmacy = false
        )

        every { getFilterPropertyWithPictureAsFLowUseCase.invoke() } returns flowOf(
            GetFilterPropertyWithPictureUseCaseResult.Success(dummyFilterDomain)
        )
        // Given
        val dummyFilterUi = FilterUi(
            types = emptyList(),
            minPrice = 1000,
            maxPrice = 5000,
            minSurface = 50,
            maxSurface = 200,
            sold = true,
            nearbySchool = true,
            nearbyShop = false,
            nearbyPark = false,
            nearbyRestaurant = false,
            nearbyPublicTransportation = true,
            nearbyPharmacy = false
        )

        // When
        viewModel = FilterViewModel(
            getFilterPropertyWithPictureAsFLowUseCase,
            resetFilterPropertyWithPictureUseCase,
            setFilterPropertyWithPictureUseCase,
            getPropertyTypeUseCase
        )

        val job = launch { viewModel.filterUiState.collect {} }
        advanceUntilIdle()

        // Then
        assertEquals(UISuccess(dummyFilterUi), viewModel.filterUiState.value)
        // And
        assertEquals(dummyFilterUi, viewModel.filter.value)

        job.cancel()

    }

    @Test
    fun `onCheckedPropertyType adds property type when checked is true and removes when false`() = runTest {
        // Given
        val dummyPropertyTypeUi = mockk<com.xavier_carpentier.realestatemanager.ui.model.PropertyTypeUi>(relaxed = true)

        val propertyTypeCheckable = com.xavier_carpentier.realestatemanager.ui.model.PropertyTypeUiCheckable(dummyPropertyTypeUi, false)

        every { getFilterPropertyWithPictureAsFLowUseCase.invoke() } returns flowOf(Empty)
        viewModel = FilterViewModel(
            getFilterPropertyWithPictureAsFLowUseCase,
            resetFilterPropertyWithPictureUseCase,
            setFilterPropertyWithPictureUseCase,
            getPropertyTypeUseCase
        )
        advanceTimeBy(6000)
        advanceUntilIdle()

        (viewModel.listOfPropertyType as MutableList).add(propertyTypeCheckable)

        // When
        viewModel.onCheckedPropertyType(propertyTypeCheckable, true)
        // Then
        assert(viewModel.filter.value.types?.contains(dummyPropertyTypeUi) == true)
        // And
        assertEquals(true, propertyTypeCheckable.checked)

        // When
        viewModel.onCheckedPropertyType(propertyTypeCheckable, false)
        // Then
        assert(viewModel.filter.value.types?.contains(dummyPropertyTypeUi) == false)

        assertEquals(false, propertyTypeCheckable.checked)


    }

    @Test
    fun `onChangeValue updates filter values for MIN_PRICE`() = runTest {
        // Given
        every { getFilterPropertyWithPictureAsFLowUseCase.invoke() } returns flowOf(Empty)
        viewModel = FilterViewModel(
            getFilterPropertyWithPictureAsFLowUseCase,
            resetFilterPropertyWithPictureUseCase,
            setFilterPropertyWithPictureUseCase,
            getPropertyTypeUseCase
        )
        advanceTimeBy(6000)
        advanceUntilIdle()
        // When
        viewModel.onChangeValue(FilterType.MIN_PRICE, "5000")
        // Then
        assertEquals(5000L, viewModel.filter.value.minPrice)


    }

    @Test
    fun `onFilterApplied calls setFilter when applied is true`() = runTest {
        // Given
        every { getFilterPropertyWithPictureAsFLowUseCase.invoke() } returns flowOf(Empty)
        viewModel = FilterViewModel(
            getFilterPropertyWithPictureAsFLowUseCase,
            resetFilterPropertyWithPictureUseCase,
            setFilterPropertyWithPictureUseCase,
            getPropertyTypeUseCase
        )

        advanceTimeBy(6000)
        advanceUntilIdle()
        // When
        viewModel.onFilterApplied(true)
        advanceUntilIdle()
        // Then: setFilterPropertyWithPictureUseCase.invoke doit être appelée avec un filtre (conversion via FilterDomainToUiMapper)
        coVerify { setFilterPropertyWithPictureUseCase.invoke(any()) }
    }

    @Test
    fun `onFilterApplied calls resetFilter when applied is false`() = runTest {
        // Given
        every { getFilterPropertyWithPictureAsFLowUseCase.invoke() } returns flowOf(Empty)
        viewModel = FilterViewModel(
            getFilterPropertyWithPictureAsFLowUseCase,
            resetFilterPropertyWithPictureUseCase,
            setFilterPropertyWithPictureUseCase,
            getPropertyTypeUseCase
        )

        advanceTimeBy(6000)
        advanceUntilIdle()
        // When
        viewModel.onFilterApplied(false)
        advanceUntilIdle()
        // Then
        coVerify { resetFilterPropertyWithPictureUseCase.invoke() }
    }
}