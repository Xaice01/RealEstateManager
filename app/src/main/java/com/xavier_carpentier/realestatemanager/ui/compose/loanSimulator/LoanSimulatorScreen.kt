package com.xavier_carpentier.realestatemanager.ui.compose.loanSimulator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.xavier_carpentier.realestatemanager.ui.compose.utils.DetailSection
import com.xavier_carpentier.realestatemanager.ui.compose.utils.TwoTextFieldInRow
import com.xavier_carpentier.realestatemanager.ui.loanSimulator.LoanSimulatorViewModel
import com.xavier_carpentier.realestatemanager.ui.theme.AppTheme

@Composable
fun LoanSimulatorScreen(viewModel: LoanSimulatorViewModel = hiltViewModel()){

    val loanAmount by viewModel.loanAmount.collectAsState()
    val duration by viewModel.duration.collectAsState()
    val rate by viewModel.rate.collectAsState()
    val monthlyPayment by viewModel.monthlyPayment.collectAsState()
    val annuallyPayment by viewModel.annuallyPayment.collectAsState()
    val costOfLoan by viewModel.costOfLoan.collectAsState()

    LoanSimulatorContent(
        loanAmountValue = loanAmount.toString(),
        onLoanAmountChange = { viewModel.onLoanAmountChange(it) },
        durationValue = duration.toString(),
        onDurationChange = { viewModel.onDurationChange(it) },
        rateValue = rate.toString(),
        onRateChange = { viewModel.onRateChange(it) },
        monthlyPayment = monthlyPayment.toString(),
        annuallyPayment = annuallyPayment.toString(),
        costOfLoan = costOfLoan.toString(),
        onCalculate = { viewModel.onCalculate() }
    )


}

@Composable
fun LoanSimulatorContent(
    loanAmountValue: String,
    onLoanAmountChange: (String) -> Unit,
    durationValue: String,
    onDurationChange: (String) -> Unit,
    rateValue: String,
    onRateChange: (String) -> Unit,
    monthlyPayment: String,
    annuallyPayment: String,
    costOfLoan :String,
    onCalculate: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        DetailSection(
            title = "Loan Simulator",
            content = {
                OutlinedTextField(
                    value = loanAmountValue,
                    onValueChange = { onLoanAmountChange(it) },
                    label = { Text("Loan Amount") },
                    keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = durationValue,
                    onValueChange = { onDurationChange(it) },
                    label = { Text("Duration (month)") },
                    keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = rateValue,
                    onValueChange = { onRateChange(it) },
                    label = { Text("Rate (%)") },
                    keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Number)
                )

                ElevatedButton(
                    onClick = { onCalculate() },
                    modifier = Modifier.padding(16.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
                ){
                    Text("Calculate")
                }
            }
        )

        Spacer(modifier = Modifier.padding(16.dp))

        ElevatedCard(
            modifier = Modifier.widthIn(max = 450.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ){
            DetailSection(
                title = "Payment",
                content = {
                    TwoTextFieldInRow(
                        firstValue = monthlyPayment,
                        secondValue = annuallyPayment,
                        firstLabel = "Monthly Payment",
                        secondLabel = "Annually Payment",
                        onFirstValueChange = {},
                        onSecondValueChange = {},
                        modifier = Modifier.padding(4.dp)
                    )
                    TwoTextFieldInRow(
                        firstValue = loanAmountValue,
                        secondValue = costOfLoan,
                        firstLabel = "Loan Amount",
                        secondLabel = "Cost of Loan",
                        onFirstValueChange = {},
                        onSecondValueChange = {},
                        modifier = Modifier.padding(4.dp)
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun LoanSimulatorContentPreview(){
    AppTheme {
        LoanSimulatorContent(
            loanAmountValue = "100000",
            onLoanAmountChange = {},
            durationValue = "36",
            onDurationChange = {},
            rateValue = "5",
            onRateChange = {},
            monthlyPayment = "1000",
            annuallyPayment = "10000",
            costOfLoan = "100000",
            onCalculate = {}
        )
    }

}