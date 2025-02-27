package com.xavier_carpentier.realestatemanager.domain.property

import com.xavier_carpentier.realestatemanager.domain.current_setting.utils.ConversePrice
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPropertyWithPictureUseCase @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val conversePrice: ConversePrice
) {
    operator fun invoke(id: Int): Flow<GetPropertyWithPictureUseCaseResult> {
        return propertyRepository.getPropertyAndPicture(id).map { property ->
            if (property == null) {
                GetPropertyWithPictureUseCaseResult.Empty
            } else {
                val propertyTemp = PropertyWithPictureDomain(
                    property.propertyDomain.copy(price = conversePrice.conversePriceToUi(property.propertyDomain.price)),
                    property.pictureDomains
                )
                GetPropertyWithPictureUseCaseResult.Success(propertyTemp)
            }
        }
    }
}

sealed interface GetPropertyWithPictureUseCaseResult{
    data object Empty : GetPropertyWithPictureUseCaseResult
    data class Success(val property : PropertyWithPictureDomain) : GetPropertyWithPictureUseCaseResult
}