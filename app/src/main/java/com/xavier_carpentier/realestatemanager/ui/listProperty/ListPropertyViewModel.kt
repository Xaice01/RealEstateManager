package com.xavier_carpentier.realestatemanager.ui.listProperty


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xavier_carpentier.realestatemanager.domain.property.GetAllPropertyAsFlowUseCase
import com.xavier_carpentier.realestatemanager.domain.property.GetOnPropertyUseCaseResult
import com.xavier_carpentier.realestatemanager.ui.mapper.PropertyWithPictureUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ListPropertyViewModel @Inject constructor(
    private val getAllPropertyAsFlowUseCase: GetAllPropertyAsFlowUseCase
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is List Property Fragment"
    }
    val text: LiveData<String> = _text


    val uiState:StateFlow<ListPropertyUIState> = getAllPropertyAsFlowUseCase()
            .map { result->
                when (result) {
                    is GetOnPropertyUseCaseResult.Empty -> ListPropertyUIState.Empty
                    is GetOnPropertyUseCaseResult.Success -> ListPropertyUIState.Success(PropertyWithPictureUiMapper.mapListToUi(result.listProperty))
                }
            }

            .onStart { delay(6000) }
            .stateIn(viewModelScope,started = SharingStarted.WhileSubscribed(
                stopTimeoutMillis = 5000 // Avoid cancelling the flow on configuration change
            ),
                initialValue = ListPropertyUIState.Loading)


    fun getAllProperties()=getAllPropertyAsFlowUseCase.invoke()
        //.asLiveData(viewModelScope.coroutineContext)



}