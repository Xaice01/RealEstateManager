package com.xavier_carpentier.realestatemanager.domain.current_setting

import javax.inject.Inject

class SetCurrentDollarRateUseCase @Inject constructor(
    private val repository: CurrentSettingRepository
) {
    operator fun invoke(currentRate: Float) {
        repository.setCurrentDollarRate(currentRate)
    }
}