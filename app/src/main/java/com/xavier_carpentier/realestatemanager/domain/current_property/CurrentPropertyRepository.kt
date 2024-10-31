package com.xavier_carpentier.realestatemanager.domain.current_property

import kotlinx.coroutines.flow.StateFlow

interface CurrentPropertyRepository {
    val currentPropertyIdFlow: StateFlow<Int?>
    fun setCurrentPropertyId(currentId: Int)
}