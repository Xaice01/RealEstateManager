package com.xavier_carpentier.realestatemanager.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xavier_carpentier.realestatemanager.domain.current_property.SetCurrentPropertyUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.GetSelectedCurrencyUseCase
import com.xavier_carpentier.realestatemanager.domain.property.GetAllPropertyFilterAsFlowUseCase
import com.xavier_carpentier.realestatemanager.domain.property.GetOnPropertyFilterUseCaseResult
import com.xavier_carpentier.realestatemanager.ui.listProperty.ListPropertyUIState
import com.xavier_carpentier.realestatemanager.ui.mapper.CurrencyDomainToUiMapper
import com.xavier_carpentier.realestatemanager.ui.mapper.PropertyWithPictureUiMapper
import com.xavier_carpentier.realestatemanager.ui.model.CurrencyUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getAllPropertyFilterAsFlowUseCase: GetAllPropertyFilterAsFlowUseCase,
    private val setCurrentPropertyUseCase: SetCurrentPropertyUseCase,
    private val getSelectedCurrencyUseCase: GetSelectedCurrencyUseCase,
    private val currencyMapper: CurrencyDomainToUiMapper
) : ViewModel() {

    private val selectedCurrency: StateFlow<CurrencyUi?> = getSelectedCurrencyUseCase()
        .map { it?.let { currencyMapper.mapToUi(it) } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val propertiesFlow = selectedCurrency.flatMapLatest {
        getAllPropertyFilterAsFlowUseCase.invoke()
    }

    val uiState: StateFlow<ListPropertyUIState> = combine(
        propertiesFlow,
        selectedCurrency
    ) { result, currency ->
        when (result) {
            is GetOnPropertyFilterUseCaseResult.Empty -> ListPropertyUIState.Empty
            is GetOnPropertyFilterUseCaseResult.Success -> {
                val propertiesWithCurrency = PropertyWithPictureUiMapper.mapListToUi(result.listProperty)
                ListPropertyUIState.Success(propertiesWithCurrency, currency ?: CurrencyUi("Dollar", "$"))
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ListPropertyUIState.Loading
    )

    fun onSelectProperty(propertyId: Int) {
        setCurrentPropertyUseCase(propertyId)
    }

}