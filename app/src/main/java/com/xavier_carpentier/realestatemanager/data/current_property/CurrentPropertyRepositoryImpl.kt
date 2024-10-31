package fr.delcey.openclassrooms_master_detail_mvvm.data.current_mail

import com.xavier_carpentier.realestatemanager.domain.current_property.CurrentPropertyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentPropertyRepositoryImpl @Inject constructor() : CurrentPropertyRepository {
    private val currentPropertyIdMutableSharedFlow = MutableStateFlow<Int?>(null)
    override val currentPropertyIdFlow: StateFlow<Int?> = currentPropertyIdMutableSharedFlow.asStateFlow()

    override fun setCurrentPropertyId(currentId: Int) {
        currentPropertyIdMutableSharedFlow.value = currentId
    }
}