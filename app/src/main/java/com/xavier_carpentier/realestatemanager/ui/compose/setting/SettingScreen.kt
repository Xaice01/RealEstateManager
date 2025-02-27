package com.xavier_carpentier.realestatemanager.ui.compose.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.xavier_carpentier.realestatemanager.ui.compose.spinner.Spinner
import com.xavier_carpentier.realestatemanager.ui.model.CurrencyUi
import com.xavier_carpentier.realestatemanager.ui.setting.SettingViewModel

@Composable
fun SettingScreen(viewModel: SettingViewModel = hiltViewModel()) {
    val currentAgent by viewModel.currentAgent.collectAsState(initial = null)
    val selectedCurrencyUi by viewModel.selectedCurrency.collectAsState(initial = null)
    val currentDollarRate by viewModel.currentDollarRate.collectAsState(initial = null)
    val agents by viewModel.agentsStateFlow.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Premier TextView
        Text(
            text = "Choisissez votre devise",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedCurrencyUi?.displayName == "Euro",
                onClick = {
                    viewModel.updateCurrency(CurrencyUi(displayName = "Euro", symbol = "€"))
                    },
                modifier = Modifier.padding(start = 4.dp)
            )
            Text("Euro", modifier = Modifier.padding(start = 4.dp))

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = selectedCurrencyUi?.displayName == "Dollar",
                onClick = { viewModel.updateCurrency(CurrencyUi(displayName = "Dollar", symbol = "$")) }
            )
            Text("Dollar", modifier = Modifier.padding(start = 4.dp))
        }

        Text(
            text = "Sélectionnez un agent",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        // Spinner
        currentAgent?.let { Spinner(nameList = "Agent", list = agents , preselected = it, onSelectionChanged = { agent -> viewModel.updateAgent(agent)} ) }


        Text(
            text = "Cours actuel du dollar/euro",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        OutlinedTextField(
            value = currentDollarRate.toString(),
            onValueChange = { viewModel.updateDollarRate(it.toFloat()) },
            label = { Text("Taux de change") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

    }
}