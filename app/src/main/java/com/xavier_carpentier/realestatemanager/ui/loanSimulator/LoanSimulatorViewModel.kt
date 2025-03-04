package com.xavier_carpentier.realestatemanager.ui.loanSimulator

import androidx.lifecycle.ViewModel
import com.xavier_carpentier.realestatemanager.domain.loanSimulator.LoanSimulatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class LoanSimulatorViewModel @Inject constructor(
    private val loanSimulatorUseCase: LoanSimulatorUseCase
) : ViewModel() {

    private val _loanAmount = MutableStateFlow<Long>(1000)
    val loanAmount= _loanAmount.asStateFlow()

    private val _duration = MutableStateFlow<Int>(12)
    val duration= _duration.asStateFlow()

    private val _rate = MutableStateFlow<Double>(3.5)
    val rate= _rate.asStateFlow()

    private val _monthlyPayment = MutableStateFlow<Double>(0.0)
    val monthlyPayment= _monthlyPayment.asStateFlow()

    private val _annuallyPayment = MutableStateFlow<Double>(0.0)
    val annuallyPayment= _annuallyPayment.asStateFlow()

    private val _costOfLoan = MutableStateFlow<Double>(0.0)
    val costOfLoan= _costOfLoan.asStateFlow()


    fun onLoanAmountChange(loanAmount:String) {
        if (loanAmount.isEmpty()) {
            _loanAmount.value = 0
        } else {
            val parsed = loanAmount.toLong()
            if (parsed <= 1_000_000_000_000_000_000) {
                _loanAmount.value = parsed
            }
        }

    }

    fun onDurationChange(duration: String) {
        if (duration.isEmpty()) {
            _duration.value = 0
        } else {
            val parsed = duration.toInt()
            if (parsed <= 1_000_000_000) {
                _duration.value = parsed
            }
        }
    }

    fun onRateChange(rate: String) {

        if (rate.isEmpty()) {
            _rate.value = 0.0
        } else {
            val parsed = rate.toDouble()
            if (parsed <= 1_000_000_000) {
                _rate.value = parsed
            }
        }
    }

    fun onCalculate() {
        calculateMonthlyPayment()
    }

    private fun calculateMonthlyPayment() {
        _monthlyPayment.value = loanSimulatorUseCase.invoke(_loanAmount.value, _duration.value, _rate.value)
        _annuallyPayment.value = round((_monthlyPayment.value * 12) * 100) / 100
        _costOfLoan.value= round(((_monthlyPayment.value* _duration.value) - _loanAmount.value)*100)/100
    }


}