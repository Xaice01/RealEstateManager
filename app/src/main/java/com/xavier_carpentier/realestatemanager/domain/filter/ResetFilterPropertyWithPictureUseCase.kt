package com.xavier_carpentier.realestatemanager.domain.filter

import javax.inject.Inject

class ResetFilterPropertyWithPictureUseCase @Inject constructor(
    private val filterRepository: FilterRepository
) {
    operator fun invoke() {
        filterRepository.resetFilter()
    }

}