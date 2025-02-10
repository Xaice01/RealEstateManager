package com.xavier_carpentier.realestatemanager.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class MapViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Map Fragment"
    }
    val text: LiveData<String> = _text
}