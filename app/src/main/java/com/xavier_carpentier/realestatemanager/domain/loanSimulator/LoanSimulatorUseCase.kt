package com.xavier_carpentier.realestatemanager.domain.loanSimulator

import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.round

class LoanSimulatorUseCase @Inject constructor() {
    operator fun invoke(loanAmount: Long, durationInMonth: Int, rate: Double): Double {

        //Annuité = (Capital * Taux d’intérêt exprimé en centième) / (1 — (1 + Taux d’intérêt)^(— Durée))
        val rateInHundredths = rate / 100
        val durationInYear = durationInMonth/12.toDouble()

        val annuallyPayment:Double =(loanAmount*rateInHundredths)/(1-((1+rateInHundredths).pow(-durationInYear)))

        val monthlyPayment = annuallyPayment / 12
        return round(monthlyPayment * 100) / 100
    }
}