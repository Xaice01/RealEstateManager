package com.xavier_carpentier.realestatemanager.domain.filter

import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetFilterIsAppliedUseCase @Inject constructor(
    private val filterRepository: FilterRepository
) {
    operator fun invoke(): StateFlow<Boolean> {
        return filterRepository.filterIsApplied()
    }
}