package com.xavier_carpentier.realestatemanager.domain.current_setting.utils

import com.xavier_carpentier.realestatemanager.domain.current_setting.GetCurrentDollarRateUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.GetSelectedCurrencyUseCase
import com.xavier_carpentier.realestatemanager.domain.current_setting.model.CurrencyType
import javax.inject.Inject

class ConversePrice @Inject constructor(
    private val getCurrentDollarRateUseCase: GetCurrentDollarRateUseCase,
    private val getSelectedCurrencyUseCase: GetSelectedCurrencyUseCase
){
    fun conversePriceToData(price: Long):Long{
        val currentDollarRate = getCurrentDollarRateUseCase()
        val selectedCurrency = getSelectedCurrencyUseCase()

        return if(selectedCurrency.value == CurrencyType.DOLLAR){
            price
        }else{
            Math.round(price / currentDollarRate.value!!).toLong()

        }

    }

    fun conversePriceToUi(price: Long):Long{
        val currentDollarRate = getCurrentDollarRateUseCase()
        val selectedCurrency = getSelectedCurrencyUseCase()

        return if(selectedCurrency.value == CurrencyType.DOLLAR){
            price
        }else{
            Math.round(price * currentDollarRate.value!!).toLong()
        }
    }

}