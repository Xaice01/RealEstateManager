package com.xavier_carpentier.realestatemanager.domain.property

import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPropertyWithPictureUseCase @Inject constructor(private val propertyRepository: PropertyRepository) {
    operator fun invoke(id: Int): Flow<GetPropertyWithPictureUseCaseResult> {
        return propertyRepository.getPropertyAndPicture(id).map { property ->
            if (property == null) {
                GetPropertyWithPictureUseCaseResult.Empty
            } else {
                GetPropertyWithPictureUseCaseResult.Success(property) // todo modified by current currency dollar: do nothing euro: convert price
            }
        }
    }
}

sealed interface GetPropertyWithPictureUseCaseResult{
    data object Empty : GetPropertyWithPictureUseCaseResult
    data class Success(val property : PropertyWithPictureDomain) : GetPropertyWithPictureUseCaseResult
}