package com.xavier_carpentier.realestatemanager.domain.current_property

import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCurrentPropertyIdUseCase @Inject constructor(private val currentPropertyRepository: CurrentPropertyRepository) {
    operator fun invoke() :StateFlow<Int?>{
        return currentPropertyRepository.currentPropertyIdFlow
    }

}