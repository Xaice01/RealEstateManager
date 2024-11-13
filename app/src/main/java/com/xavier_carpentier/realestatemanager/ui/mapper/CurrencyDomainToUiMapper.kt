package com.xavier_carpentier.realestatemanager.ui.mapper

import com.xavier_carpentier.realestatemanager.domain.current_setting.model.CurrencyType
import com.xavier_carpentier.realestatemanager.ui.model.CurrencyUi
import javax.inject.Inject

class CurrencyDomainToUiMapper @Inject constructor() {
    fun mapToUi(type: CurrencyType): CurrencyUi {
        return when (type) {
            CurrencyType.EURO -> CurrencyUi(displayName = "Euro", symbol = "€")
            CurrencyType.DOLLAR -> CurrencyUi(displayName = "Dollar", symbol = "$")
        }
    }

    fun mapToDomain(uiModel: CurrencyUi): CurrencyType {
        return when (uiModel.displayName) {
            "Euro" -> CurrencyType.EURO
            "Dollar" -> CurrencyType.DOLLAR
            else -> throw IllegalArgumentException("Unknown currency: ${uiModel.displayName}")
        }
    }
}