package com.xavier_carpentier.realestatemanager.ui.listProperty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListPropertyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is List Property Fragment"
    }
    val text: LiveData<String> = _text
}