package com.xavier_carpentier.realestatemanager.ui.main

import androidx.lifecycle.ViewModel
import com.xavier_carpentier.realestatemanager.domain.filter.GetFilterIsAppliedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFilterIsAppliedUseCase : GetFilterIsAppliedUseCase,
) :ViewModel(){

    private val _isFilter = getFilterIsAppliedUseCase.invoke()
    val isFilter = _isFilter

}