package com.xavier_carpentier.realestatemanager.domain.property

import com.xavier_carpentier.realestatemanager.domain.current_setting.utils.ConversePrice
import com.xavier_carpentier.realestatemanager.domain.filter.GetFilterIsAppliedUseCase
import com.xavier_carpentier.realestatemanager.domain.filter.GetFilterPropertyWithPictureAsFLowUseCase
import com.xavier_carpentier.realestatemanager.domain.filter.GetFilterPropertyWithPictureUseCaseResult
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllPropertyFilterAsFlowUseCase @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val conversePrice: ConversePrice,
    private val getFilterPropertyWithPictureAsFLowUseCase: GetFilterPropertyWithPictureAsFLowUseCase,
    private val getAllPropertyAsFlowUseCase: GetAllPropertyAsFlowUseCase,
    private val getFilterIsAppliedUseCase: GetFilterIsAppliedUseCase
) {
   @OptIn(ExperimentalCoroutinesApi::class)
   operator fun invoke(): Flow<GetOnPropertyFilterUseCaseResult> {
       return combine(
           getFilterPropertyWithPictureAsFLowUseCase(),
           getFilterIsAppliedUseCase()
       ) { filterResult, isApplied ->
           when {
               isApplied && filterResult is GetFilterPropertyWithPictureUseCaseResult.Success -> {
                   val filter = filterResult.filter
                   propertyRepository.getFilteredPropertyAndPicture(
                       filter
                   ).map { propertyList ->
                       if (propertyList.isNullOrEmpty()) {
                           GetOnPropertyFilterUseCaseResult.Empty
                       } else {
                           GetOnPropertyFilterUseCaseResult.Success(
                               propertyList.map { property ->
                                   PropertyWithPictureDomain(
                                       property.propertyDomain.copy(
                                           price = conversePrice.conversePriceToUi(property.propertyDomain.price)
                                       ),
                                       property.pictureDomains
                                   )
                               }
                           )
                       }
                   }
               }
               else -> {
                   getAllPropertyAsFlowUseCase().map { result ->
                       when (result) {
                           is GetOnPropertyUseCaseResult.Empty -> GetOnPropertyFilterUseCaseResult.Empty
                           is GetOnPropertyUseCaseResult.Success -> GetOnPropertyFilterUseCaseResult.Success(result.listProperty)
                       }
                   }
               }
           }
       }.flattenMerge()
   }
}


sealed interface GetOnPropertyFilterUseCaseResult{
    data object Empty : GetOnPropertyFilterUseCaseResult
    data class Success(val listProperty : List<PropertyWithPictureDomain> ) : GetOnPropertyFilterUseCaseResult
}