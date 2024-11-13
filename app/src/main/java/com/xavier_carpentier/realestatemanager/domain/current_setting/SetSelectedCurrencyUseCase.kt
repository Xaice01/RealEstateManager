package com.xavier_carpentier.realestatemanager.domain.current_setting

import com.xavier_carpentier.realestatemanager.domain.current_setting.model.CurrencyType
import javax.inject.Inject

class SetSelectedCurrencyUseCase @Inject constructor(
    private val repository: CurrentSettingRepository
) {
    operator fun invoke(currencyType: CurrencyType) {
        repository.setSelectedCurrency(currencyType)
    }
}