package com.xavier_carpentier.realestatemanager.domain.loanSimulator

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class LoanSimulatorUseCaseTest {

    @Test
    fun `invoke returns correct monthly payment for a 10-year loan at 5 percent`() = runTest {
        // Given: a LoanSimulatorUseCase instance, a loan amount of 100000, duration 120 months, and a rate of 5.0%
        val useCase = LoanSimulatorUseCase()
        val loanAmount: Long = 100000
        val durationInMonth = 120
        val rate = 5.0

        // When: invoking the use case to calculate the monthly payment
        val monthlyPayment = useCase.invoke(loanAmount, durationInMonth, rate)

        // Then: the monthly payment should be approximately 1079.25 (rounded to 2 decimals)
        assertEquals(1079.2, monthlyPayment, 0.01)
    }

    @Test
    fun `invoke returns correct monthly payment for a 30-year loan at 3_5 percent`() = runTest {
        // Given: a LoanSimulatorUseCase instance, a loan amount of 200000, duration 360 months, and a rate of 3.5%
        val useCase = LoanSimulatorUseCase()
        val loanAmount: Long = 200000
        val durationInMonth = 360
        val rate = 3.5

        // When: invoking the use case to calculate the monthly payment
        val monthlyPayment = useCase.invoke(loanAmount, durationInMonth, rate)

        // Then: the monthly payment should be approximately 906.38 (rounded to 2 decimals)
        assertEquals(906.19, monthlyPayment, 0.01)
    }

    @Test
    fun `invoke returns zero monthly payment when loan amount is zero`() = runTest {
        // Given: a LoanSimulatorUseCase instance with a loan amount of 0, any duration, and any rate
        val useCase = LoanSimulatorUseCase()
        val loanAmount: Long = 0
        val durationInMonth = 120
        val rate = 5.0

        // When: invoking the use case
        val monthlyPayment = useCase.invoke(loanAmount, durationInMonth, rate)

        // Then: the monthly payment should be 0.0
        assertEquals(0.0, monthlyPayment, 0.0)
    }
}