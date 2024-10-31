package com.xavier_carpentier.realestatemanager.domain.current_property

import javax.inject.Inject


class SetCurrentPropertyUseCase @Inject constructor(private val currentPropertyRepository: CurrentPropertyRepository) {
    operator fun invoke(id :Int){
        currentPropertyRepository.setCurrentPropertyId(id)
    }
}