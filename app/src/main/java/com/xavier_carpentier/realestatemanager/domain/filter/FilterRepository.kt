package com.xavier_carpentier.realestatemanager.domain.filter

import com.xavier_carpentier.realestatemanager.domain.filter.model.FilterDomain
import kotlinx.coroutines.flow.StateFlow

interface FilterRepository {
    fun setFilter(filter: FilterDomain)
    fun getFilter(): StateFlow<FilterDomain?>
    fun resetFilter()
    fun filterIsApplied(): StateFlow<Boolean>
}