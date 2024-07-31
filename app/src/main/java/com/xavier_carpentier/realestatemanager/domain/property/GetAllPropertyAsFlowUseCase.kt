package com.xavier_carpentier.realestatemanager.domain.property

import com.xavier_carpentier.realestatemanager.ui.mapper.PropertyWithPictureUiMapper
import com.xavier_carpentier.realestatemanager.ui.model.PropertyWithPictureUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllPropertyAsFlowUseCase @Inject constructor( private val propertyRepository: PropertyRepository) {

    fun invoke() : Flow<List<PropertyWithPictureUi>?> {

        return propertyRepository.getAllPropertyAndPicture().map { property ->
            property?.let {
                PropertyWithPictureUiMapper.mapListToUi(it)
            }
        }
    }
}