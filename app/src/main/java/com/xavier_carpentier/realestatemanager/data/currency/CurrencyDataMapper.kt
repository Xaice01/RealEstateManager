package com.xavier_carpentier.realestatemanager.data.currency

import com.xavier_carpentier.realestatemanager.domain.current_setting.model.CurrencyType
import javax.inject.Inject

class CurrencyDataMapper @Inject constructor() {
    fun mapToDomain(entity: CurrencyEntity): CurrencyType {
        return when (entity.currencyCode) {
            "EUR" -> CurrencyType.EURO
            "USD" -> CurrencyType.DOLLAR
            else -> throw IllegalArgumentException("Unknown currency code")
        }
    }

    fun mapToData(type: CurrencyType): CurrencyEntity {
        val currencyCode = when (type) {
            CurrencyType.EURO -> "EUR"
            CurrencyType.DOLLAR -> "USD"
        }
        return CurrencyEntity(currencyCode)
    }
}