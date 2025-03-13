package com.xavier_carpentier.realestatemanager.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xavier_carpentier.realestatemanager.domain.agent.GetAgentsUseCase
import com.xavier_carpentier.realestatemanager.domain.current_property.GetCurrentPropertyIdUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.GetCurrentAgentUseCase
import com.xavier_carpentier.realestatemanager.domain.picture.DeletePictureUseCase
import com.xavier_carpentier.realestatemanager.domain.picture.InsertPictureUseCase
import com.xavier_carpentier.realestatemanager.domain.property.GetPropertyWithPictureUseCase
import com.xavier_carpentier.realestatemanager.domain.property.GetPropertyWithPictureUseCaseResult
import com.xavier_carpentier.realestatemanager.domain.property.InsertPropertyWithPictureUseCase
import com.xavier_carpentier.realestatemanager.domain.property.UpdatePropertyWithPictureUseCase
import com.xavier_carpentier.realestatemanager.domain.property_type.GetPropertyTypeUseCase
import com.xavier_carpentier.realestatemanager.ui.mapper.AgentDomainToUiMapper
import com.xavier_carpentier.realestatemanager.ui.mapper.PictureUiMapper
import com.xavier_carpentier.realestatemanager.ui.mapper.PropertyTypeDomainToUiMapper
import com.xavier_carpentier.realestatemanager.ui.mapper.PropertyWithPictureUiMapper
import com.xavier_carpentier.realestatemanager.ui.model.AgentUi
import com.xavier_carpentier.realestatemanager.ui.model.PictureUi
import com.xavier_carpentier.realestatemanager.ui.model.PropertyTypeUi
import com.xavier_carpentier.realestatemanager.ui.model.PropertyUi
import com.xavier_carpentier.realestatemanager.ui.model.PropertyWithPictureUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CreatePropertyViewModel @Inject constructor(
    private val getPropertyWithPictureUseCase: GetPropertyWithPictureUseCase,
    private val getCurrentPropertyIdUseCase: GetCurrentPropertyIdUseCase,
    private val insertPropertyWithPictureUseCase: InsertPropertyWithPictureUseCase,
    private val updatePropertyWithPictureUseCase: UpdatePropertyWithPictureUseCase,
    private val insertPictureUseCase: InsertPictureUseCase,
    private val deletePictureUseCase: DeletePictureUseCase,
    private val getAgentsUseCase: GetAgentsUseCase,
    getCurrentAgentUseCase: GetCurrentAgentUseCase,
    private val getPropertyTypeUseCase: GetPropertyTypeUseCase
) : ViewModel() {
    private val propertyWithNullValue = PropertyUi(
        id = 0,
        type = getPropertyTypeUseCase()[0].databaseName,
        price = 0L,
        address = "",
        latitude = 0.0,
        longitude = 0.0,
        surface = 0L,
        room = 0,
        bedroom = 0,
        description = "",
        entryDate = Calendar.getInstance(),
        soldDate = null,
        sold = false,
        agent = getCurrentAgentUseCase.invoke().value?.agentName ?: "",
        interestNearbySchool = false,
        interestNearbyShop = false,
        interestNearbyPark = false,
        interestNearbyRestaurant = false,
        interestNearbyPublicTransportation = false,
        interestNearbyPharmacy = false
    )

    private val _property = MutableStateFlow<PropertyUi>(propertyWithNullValue)
    val property: StateFlow<PropertyUi> = _property

    private val _pictures = MutableStateFlow<List<PictureUi>>(emptyList())
    val pictures: StateFlow<List<PictureUi>> = _pictures

    private val _createMode = MutableStateFlow(false)
    val createMode: StateFlow<Boolean> = _createMode

    private val _propertyCreated = MutableStateFlow<Boolean?>(null)
    val propertyCreated: StateFlow<Boolean?> = _propertyCreated


    fun initialised(createMode: Boolean) {
        println("DEBUG: Initialization called with createMode = $createMode")
        _createMode.value = createMode
        if (!createMode) {
            viewModelScope.launch {
                getCurrentPropertyIdUseCase().collect { propertyId ->
                    println("DEBUG: getCurrentPropertyIdUseCase exécuté")

                    propertyId?.let {
                        getPropertyWithPictureUseCase(it).collect { result ->
                            when (result) {
                                is GetPropertyWithPictureUseCaseResult.Success -> {
                                    val propertyWithPictureUi =
                                        PropertyWithPictureUiMapper.mapToUi(result.property)
                                    _property.value = propertyWithPictureUi.propertyUi
                                    _pictures.value = propertyWithPictureUi.picturesUi
                                    println("DEBUG: List of images retrieved from the ViewModel: ${_pictures.value.size}")
                                }

                                is GetPropertyWithPictureUseCaseResult.Empty -> {
                                    _property.value = propertyWithNullValue
                                    _pictures.value = emptyList()
                                    println("DEBUG: No image found, list cleared")
                                }
                            }
                        }
                    }

                }
            }
        } else {
            _property.value = propertyWithNullValue
            _pictures.value = emptyList()
        }
    }

    fun createOrModifyProperty(property: PropertyUi) {
        if(property.sold && property.soldDate==null){
            property.soldDate = Calendar.getInstance()
        }
        _property.value = property
        if (_createMode.value) {
            createProperty(PropertyWithPictureUi(_property.value, _pictures.value))
        } else {
            updateProperty(PropertyWithPictureUi(_property.value, _pictures.value))
        }
    }

    private fun createProperty(property: PropertyWithPictureUi) {
        viewModelScope.launch {
            val isSuccess = insertPropertyWithPictureUseCase(
                PropertyWithPictureUiMapper.mapToDomain(property)
            )
            _propertyCreated.value = isSuccess
        }
    }

    private fun updateProperty(updatedProperty: PropertyWithPictureUi) {
        viewModelScope.launch {
            updatePropertyWithPictureUseCase(
                PropertyWithPictureUiMapper.mapToDomain(updatedProperty)
            )
            _property.value = updatedProperty.propertyUi
            _propertyCreated.value = true

        }
    }

    fun addPicture(picture: PictureUi) {
        if(_createMode.value) {
            _pictures.value = (_pictures.value + picture).toList()
            println("DEBUG: New photo list: ${_pictures.value.size}") // Add a log to check the update
        }else {
            viewModelScope.launch {
                insertPictureUseCase(PictureUiMapper.mapToDomain(picture))
            }
        }
    }

    fun clearPropertyCreatedState() {
        _propertyCreated.value = null
    }

    fun deletePicture(picture: PictureUi) {
        if(_createMode.value){
            _pictures.value = _pictures.value.filterNot { it.id == picture.id }
        }else{
            viewModelScope.launch {
                deletePictureUseCase(PictureUiMapper.mapToDomain(picture))
            }
        }
    }

    fun getAgentList(): List<AgentUi> =
        AgentDomainToUiMapper().mapListToUi(getAgentsUseCase())


    fun getTypeList(): List<PropertyTypeUi> =
        PropertyTypeDomainToUiMapper().mapListToUi(getPropertyTypeUseCase())

}