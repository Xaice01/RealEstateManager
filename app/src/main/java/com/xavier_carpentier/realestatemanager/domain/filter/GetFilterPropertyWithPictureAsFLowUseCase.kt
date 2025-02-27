package com.xavier_carpentier.realestatemanager.domain.filter

import com.xavier_carpentier.realestatemanager.domain.filter.model.FilterDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFilterPropertyWithPictureAsFLowUseCase @Inject constructor(
    private val filterRepository: FilterRepository
) {
     operator fun invoke(): Flow<GetFilterPropertyWithPictureUseCaseResult> {
         return filterRepository.getFilter().map { filter ->
             if (filter == null) {
                 GetFilterPropertyWithPictureUseCaseResult.Empty
             } else {
                GetFilterPropertyWithPictureUseCaseResult.Success(filter)
             }
         }
     }
}

sealed interface GetFilterPropertyWithPictureUseCaseResult{
    data object Empty : GetFilterPropertyWithPictureUseCaseResult
    data class Success(val filter : FilterDomain) : GetFilterPropertyWithPictureUseCaseResult
}