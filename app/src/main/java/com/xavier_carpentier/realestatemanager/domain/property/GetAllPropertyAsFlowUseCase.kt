package com.xavier_carpentier.realestatemanager.domain.property

import com.xavier_carpentier.realestatemanager.domain.current_setting.utils.ConversePrice
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllPropertyAsFlowUseCase @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val conversePrice: ConversePrice

) {

    operator fun invoke() : Flow<GetOnPropertyUseCaseResult> {

        return propertyRepository.getAllPropertyAndPicture().map { property ->
            if (property.isNullOrEmpty()){
                GetOnPropertyUseCaseResult.Empty
            }else{
                property.map { propertyDomainWithPicture ->
                    PropertyWithPictureDomain(propertyDomainWithPicture.propertyDomain.copy(price = conversePrice.conversePriceToUi(propertyDomainWithPicture.propertyDomain.price)), propertyDomainWithPicture.pictureDomains)
                }
                GetOnPropertyUseCaseResult.Success(property)
            }
        }
    }
}
sealed interface GetOnPropertyUseCaseResult{
    data object Empty : GetOnPropertyUseCaseResult
    data class Success(val listProperty : List<PropertyWithPictureDomain> ) : GetOnPropertyUseCaseResult
}