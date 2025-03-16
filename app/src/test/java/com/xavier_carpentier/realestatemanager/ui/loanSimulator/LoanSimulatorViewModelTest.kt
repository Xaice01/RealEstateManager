package com.xavier_carpentier.realestatemanager.ui.loanSimulator

import com.xavier_carpentier.realestatemanager.domain.loanSimulator.LoanSimulatorUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoanSimulatorViewModelTest {

    private lateinit var viewModel: LoanSimulatorViewModel

    @Before
    fun setup() {
        // Given
        viewModel = LoanSimulatorViewModel(LoanSimulatorUseCase())
    }

    @Test
    fun `initial state should be set to default values`() = runTest {
        // Then
        assertEquals(1000, viewModel.loanAmount.value)
        assertEquals(12, viewModel.duration.value)
        assertEquals(3.5, viewModel.rate.value, 0.0)
        assertEquals(0.0, viewModel.monthlyPayment.value, 0.0)
        assertEquals(0.0, viewModel.annuallyPayment.value, 0.0)
        assertEquals(0.0, viewModel.costOfLoan.value, 0.0)
    }

    @Test
    fun `onLoanAmountChange should update loanAmount state`() = runTest {
        // Given
        val newLoanAmount = "200000"
        // When
        viewModel.onLoanAmountChange(newLoanAmount)
        // Then
        assertEquals(200000, viewModel.loanAmount.value)
    }

    @Test
    fun `onDurationChange should update duration state`() = runTest {
        // Given
        val newDuration = "60"
        // When
        viewModel.onDurationChange(newDuration)
        // Then
        assertEquals(60, viewModel.duration.value)
    }

    @Test
    fun `onRateChange should update rate state`() = runTest {
        // Given
        val newRate = "4.5"
        // When
        viewModel.onRateChange(newRate)
        // Then
        assertEquals(4.5, viewModel.rate.value, 0.0)
    }

    @Test
    fun `onCalculate should update monthlyPayment, annuallyPayment and costOfLoan correctly`() = runTest {
        // Given
        viewModel.onLoanAmountChange("100000")  // Capital = 100000
        viewModel.onDurationChange("120")         // Durée = 120 mois (10 ans)
        viewModel.onRateChange("5.0")             // Taux = 5.0%
        advanceUntilIdle()

        // When
        viewModel.onCalculate()
        advanceUntilIdle()

        // Calcul :
        // rateInHundredths = 5.0 / 100 = 0.05
        // durée in years = 120 / 12 = 10
        // AnnuallyPayment = (100000 * 0.05) / (1 - (1 + 0.05)^(-10))
        // Calcul : (5000) / (1 - 1.05^-10) ; 1.05^-10 ≈ 0.61391, donc denom ≈ 0.38609,
        // AnnuallyPayment ≈ 5000 / 0.38609 ≈ 12950.4
        // MonthlyPayment = 12953.22 / 12 ≈ 1079.2
        // AnnuallyPayment = round(1079.2*12*100)/100 ≈ 12950.4
        // CostOfLoan = (MonthlyPayment * duration) - loanAmount = (1079.2 * 120) - 100000 ≈ 29504.0

        // Then
        val expectedMonthlyPayment = 1079.2
        val expectedAnnuallyPayment = 12950.4
        val expectedCostOfLoan = 29504.0

        assertEquals(expectedMonthlyPayment, viewModel.monthlyPayment.value, 0.01)
        assertEquals(expectedAnnuallyPayment, viewModel.annuallyPayment.value, 0.01)
        assertEquals(expectedCostOfLoan, viewModel.costOfLoan.value, 0.01)
    }
}