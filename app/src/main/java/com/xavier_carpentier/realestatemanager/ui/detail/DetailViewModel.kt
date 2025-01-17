package com.xavier_carpentier.realestatemanager.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xavier_carpentier.realestatemanager.domain.current_property.GetCurrentPropertyIdUseCase
import com.xavier_carpentier.realestatemanager.domain.property.GetPropertyWithPictureUseCase
import com.xavier_carpentier.realestatemanager.domain.property.GetPropertyWithPictureUseCaseResult
import com.xavier_carpentier.realestatemanager.ui.mapper.PropertyWithPictureUiMapper
import com.xavier_carpentier.realestatemanager.ui.model.PropertyWithPictureUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPropertyWithPictureUseCase: GetPropertyWithPictureUseCase,
    private val getCurrentPropertyIdUseCase: GetCurrentPropertyIdUseCase
) : ViewModel() {

    private val _propertyUiState = MutableStateFlow<PropertyWithPictureUi?>(null)


    private val currentPropertyId = getCurrentPropertyIdUseCase.invoke()

    val uiState: StateFlow<PropertyWithPictureUIState> = getPropertyWithPictureUseCase(currentPropertyId.value!!)
        .map { result ->
            when (result) {
                is GetPropertyWithPictureUseCaseResult.Empty -> PropertyWithPictureUIState.Empty
                is GetPropertyWithPictureUseCaseResult.Success -> {
                    val propertyUi= PropertyWithPictureUiMapper.mapToUi(result.property)
                    _propertyUiState.value = propertyUi
                    PropertyWithPictureUIState.Success (propertyUi)
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),PropertyWithPictureUIState.Loading)

    fun getMapUrl(): String{
        val latitude = _propertyUiState.value?.propertyUi?.latitude
        val longitude =_propertyUiState.value?.propertyUi?.longitude
        val zoom = 14
        val width = 400
        val height = 400
        //TODO put the api key
        val apiKey = "Your_Google_Maps_API_Key"

        return "https://maps.googleapis.com/maps/api/staticmap?" +
                "center=$latitude,$longitude" +
                "&zoom=$zoom" +
                "&size=${width}x$height" +
                "&maptype=roadmap" +
                "&key=$apiKey"
    }
}