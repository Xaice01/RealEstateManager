package com.xavier_carpentier.realestatemanager.ui.filter

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xavier_carpentier.realestatemanager.R
import com.xavier_carpentier.realestatemanager.domain.filter.GetFilterPropertyWithPictureAsFLowUseCase
import com.xavier_carpentier.realestatemanager.domain.filter.GetFilterPropertyWithPictureUseCaseResult
import com.xavier_carpentier.realestatemanager.domain.filter.ResetFilterPropertyWithPictureUseCase
import com.xavier_carpentier.realestatemanager.domain.filter.SetFilterPropertyWithPictureUseCase
import com.xavier_carpentier.realestatemanager.domain.property_type.GetPropertyTypeUseCase
import com.xavier_carpentier.realestatemanager.ui.mapper.FilterDomainToUiMapper
import com.xavier_carpentier.realestatemanager.ui.mapper.PropertyTypeDomainToUiMapper
import com.xavier_carpentier.realestatemanager.ui.model.FilterType
import com.xavier_carpentier.realestatemanager.ui.model.FilterUi
import com.xavier_carpentier.realestatemanager.ui.model.PropertyTypeUiCheckable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val getFilterPropertyWithPictureAsFLowUseCase: GetFilterPropertyWithPictureAsFLowUseCase,
    private val resetFilterPropertyWithPictureUseCase: ResetFilterPropertyWithPictureUseCase,
    private val setFilterPropertyWithPictureUseCase: SetFilterPropertyWithPictureUseCase,
    private val getPropertyTypeUseCase: GetPropertyTypeUseCase
) : ViewModel() {

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

    private val _propertyTypeUiCheckable = getPropertyTypeUseCase.invoke().map { propertyTypes ->
            PropertyTypeUiCheckable(PropertyTypeDomainToUiMapper().mapToUi(propertyTypes), false)
        }.toMutableStateList()
    val listOfPropertyType: List<PropertyTypeUiCheckable>
        get() = _propertyTypeUiCheckable


    private val _filter = MutableStateFlow<FilterUi>(emptyFilter)
    val filter= _filter.asStateFlow()

    val filterUiState = getFilterPropertyWithPictureAsFLowUseCase()
        .map{ result ->
            when (result) {
                is GetFilterPropertyWithPictureUseCaseResult.Empty ->{
                    _filter.value = emptyFilter
                    FilterUiState.Empty
                }
                is GetFilterPropertyWithPictureUseCaseResult.Success -> {
                    val filterUi = FilterDomainToUiMapper.mapToUi(result.filter)
                    _filter.value = filterUi
                    FilterUiState.Success(filterUi)
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FilterUiState.Loading)


    val checkedStates: List<Triple<FilterType, Boolean?, Int>>
        get() = listOf(
            Triple(FilterType.SOLD, _filter.value.sold, R.string.sold),
            Triple(FilterType.NEARBY_SCHOOL, _filter.value.nearbySchool, R.string.school),
            Triple(FilterType.NEARBY_SHOP, _filter.value.nearbyShop, R.string.shop),
            Triple(FilterType.NEARBY_PARK, _filter.value.nearbyPark, R.string.park),
            Triple(FilterType.NEARBY_RESTAURANT, _filter.value.nearbyRestaurant, R.string.restaurant),
            Triple(FilterType.NEARBY_PUBLIC_TRANSPORTATION, _filter.value.nearbyPublicTransportation, R.string.transport),
            Triple(FilterType.NEARBY_PHARMACY, _filter.value.nearbyPharmacy, R.string.pharmacy)
        )


    fun onCheckedPropertyType(propertyTypeUiCheckable: PropertyTypeUiCheckable, checked: Boolean) {
        listOfPropertyType.find{it.propertyTypeUi== propertyTypeUiCheckable.propertyTypeUi}?.let {propertyTypeUiCheckable ->
            if (checked){
                _filter.value = _filter.value.copy(types=_filter.value.types!!.plus(propertyTypeUiCheckable.propertyTypeUi))
            }else{
                _filter.value = _filter.value.copy(types=_filter.value.types!!.minus(propertyTypeUiCheckable.propertyTypeUi))
            }
            propertyTypeUiCheckable.checked = checked
        }
    }

    fun onChangeValue(label: FilterType, value: Any) {
        when (label) {
            FilterType.MIN_PRICE -> _filter.value = _filter.value.copy(
                minPrice = (value as? String)?.toLongOrNull() ?: 0L
            )
            FilterType.MAX_PRICE -> _filter.value = _filter.value.copy(
                maxPrice = (value as? String)?.toLongOrNull() ?: 0L
            )
            FilterType.MIN_SURFACE -> _filter.value = _filter.value.copy(
                minSurface = (value as? String)?.toLongOrNull() ?: 0L
            )
            FilterType.MAX_SURFACE -> _filter.value = _filter.value.copy(
                maxSurface = (value as? String)?.toLongOrNull() ?: 0L
            )

            FilterType.SOLD -> _filter.value = _filter.value.copy(sold = value as Boolean)
            FilterType.NEARBY_SCHOOL -> _filter.value = _filter.value.copy(nearbySchool = value as Boolean)
            FilterType.NEARBY_SHOP -> _filter.value = _filter.value.copy(nearbyShop = value as Boolean)
            FilterType.NEARBY_PARK -> _filter.value = _filter.value.copy(nearbyPark = value as Boolean)
            FilterType.NEARBY_RESTAURANT -> _filter.value = _filter.value.copy(nearbyRestaurant = value as Boolean)
            FilterType.NEARBY_PUBLIC_TRANSPORTATION -> _filter.value = _filter.value.copy(nearbyPublicTransportation = value as Boolean)
            FilterType.NEARBY_PHARMACY -> _filter.value = _filter.value.copy(nearbyPharmacy = value as Boolean)
        }
    }

    fun onFilterApplied(applied: Boolean?) {
        when (applied) {
            true -> setFilter(filter.value)
            false -> resetFilter()
            null -> {}
        }
    }



    fun resetFilter() {
        resetFilterPropertyWithPictureUseCase.invoke()
    }

    fun setFilter(filter: FilterUi) {
        setFilterPropertyWithPictureUseCase.invoke(FilterDomainToUiMapper.mapToDomain(filter))
    }




}