package com.xavier_carpentier.realestatemanager.domain.property

import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllPropertyAsFlowUseCase @Inject constructor( private val propertyRepository: PropertyRepository) {

    operator fun invoke() : Flow<GetOnPropertyUseCaseResult> {

        return propertyRepository.getAllPropertyAndPicture().map { property ->
            if (property.isNullOrEmpty()){
                GetOnPropertyUseCaseResult.Empty
            }else{
                GetOnPropertyUseCaseResult.Success(property)
            }
        }
    }
}
sealed interface GetOnPropertyUseCaseResult{
    data object Empty : GetOnPropertyUseCaseResult
    data class Success(val listProperty : List<PropertyWithPictureDomain> ) : GetOnPropertyUseCaseResult
}