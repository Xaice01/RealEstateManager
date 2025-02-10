package com.xavier_carpentier.realestatemanager.ui.loanSimulator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class LoanSimulatorViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Loan Simulator Fragment"
    }
    val text: LiveData<String> = _text
}