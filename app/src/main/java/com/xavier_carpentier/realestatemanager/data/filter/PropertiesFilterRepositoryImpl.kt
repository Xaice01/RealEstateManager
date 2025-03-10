package com.xavier_carpentier.realestatemanager.data.filter

import com.xavier_carpentier.realestatemanager.data.filter.model.Filter
import com.xavier_carpentier.realestatemanager.data.filter.model.FilterMapper
import com.xavier_carpentier.realestatemanager.domain.filter.FilterRepository
import com.xavier_carpentier.realestatemanager.domain.filter.model.FilterDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class PropertiesFilterRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FilterRepository{

    private val _filterMutableStateFlow = MutableStateFlow<Filter?>(null)
    private val filterStateFlow :StateFlow<Filter?> = _filterMutableStateFlow
    private val filterIsAppliedMutableSharedFlow = MutableStateFlow<Boolean>(false)

    override fun setFilter(filter: FilterDomain) {
        _filterMutableStateFlow.value = FilterMapper().mapToData(filter)
        filterIsAppliedMutableSharedFlow.value = true
    }

    override fun getFilter(): StateFlow<FilterDomain?> {
        return filterStateFlow
            .map { filter -> filter?.let { FilterMapper().mapToDomain(it) } }
            .stateIn(
                scope = CoroutineScope(dispatcher + SupervisorJob()),
                started = SharingStarted.Eagerly,
                initialValue = null
            )
    }

    override fun resetFilter() {
        _filterMutableStateFlow.value = null
        filterIsAppliedMutableSharedFlow.value = false
    }

    override fun filterIsApplied(): StateFlow<Boolean> {
        return filterIsAppliedMutableSharedFlow.asStateFlow()
    }

}