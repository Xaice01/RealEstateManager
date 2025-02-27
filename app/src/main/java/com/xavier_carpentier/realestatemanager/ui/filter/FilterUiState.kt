package com.xavier_carpentier.realestatemanager.ui.filter

import com.xavier_carpentier.realestatemanager.ui.model.FilterUi

sealed interface FilterUiState {
    data object Empty:FilterUiState
    data object Loading:FilterUiState
    data class Success(val filter: FilterUi) :FilterUiState
}