package com.xavier_carpentier.realestatemanager.ui.main

import androidx.lifecycle.ViewModel
import com.xavier_carpentier.realestatemanager.domain.current_property.GetCurrentPropertyIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getCurrentPropertyIdUseCase : GetCurrentPropertyIdUseCase) :ViewModel(){

    private var isTablet: Boolean =false



    fun getIsTablet(): Boolean{
        return isTablet
    }

    fun onResume(isTablet: Boolean){
        this.isTablet= isTablet
    }
}