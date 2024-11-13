package com.xavier_carpentier.realestatemanager.domain.current_setting

import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCurrentDollarRateUseCase @Inject constructor(
    private val repository: CurrentSettingRepository
) {
    operator fun invoke(): StateFlow<Float?> {
        return repository.currentDollarRate
    }
}