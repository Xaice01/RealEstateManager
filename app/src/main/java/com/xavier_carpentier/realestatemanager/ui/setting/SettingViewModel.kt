package com.xavier_carpentier.realestatemanager.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingViewModel : ViewModel() {


    private val _selectedCurrency = MutableLiveData<String>()
    val selectedCurrency: LiveData<String> = _selectedCurrency

    private val _selectedAgent = MutableLiveData<String>()
    val selectedAgent: LiveData<String> = _selectedAgent

    private val _dollarRate = MutableLiveData<String>()
    val dollarRate: LiveData<String> = _dollarRate

    fun setSelectedCurrency(currency: String) {
        _selectedCurrency.value = currency
    }

    fun setSelectedAgent(agent: String) {
        _selectedAgent.value = agent
    }

    fun setDollarRate(rate: String) {
        _dollarRate.value = rate
    }

    //fun saveinfo()

}