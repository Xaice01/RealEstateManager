package com.xavier_carpentier.realestatemanager.domain.current_setting

import com.xavier_carpentier.realestatemanager.domain.current_setting.model.CurrencyType
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetSelectedCurrencyUseCase @Inject constructor(
    private val repository: CurrentSettingRepository
) {
    operator fun invoke(): StateFlow<CurrencyType?> {
        return repository.selectedCurrencyFlow
    }
}