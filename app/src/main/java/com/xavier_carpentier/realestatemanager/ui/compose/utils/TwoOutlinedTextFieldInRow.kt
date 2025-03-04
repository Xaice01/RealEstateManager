package com.xavier_carpentier.realestatemanager.ui.compose.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TwoOutlinedTextFieldInRow(
    firstValue: String,
    secondValue: String,
    onFirstValueChange: (String) -> Unit,
    onSecondValueChange: (String) -> Unit,
    firstLabel: String,
    secondLabel: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier
){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = firstValue,
            onValueChange = { onFirstValueChange(it) },
            label = { Text(firstLabel) },
            keyboardOptions =keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = modifier.weight(1f)
        )
        OutlinedTextField(
            value = secondValue,
            onValueChange = { onSecondValueChange(it) },
            label = { Text(secondLabel) },
            keyboardOptions =keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = modifier.weight(1f)
        )
    }
}

@Composable
fun TwoTextFieldInRow(
    firstValue: String,
    secondValue: String,
    onFirstValueChange: (String) -> Unit,
    onSecondValueChange: (String) -> Unit,
    firstLabel: String,
    secondLabel: String,
    readOnly: Boolean = true,
    modifier: Modifier = Modifier
){

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        TextField(
            value = firstValue,
            readOnly = readOnly,
            onValueChange = { onFirstValueChange(it) },
            label = { Text(firstLabel) },
            modifier = modifier.weight(1f)
        )
        TextField(
            value = secondValue,
            readOnly = readOnly,
            onValueChange = { onSecondValueChange(it) },
            label = { Text(secondLabel) },
            modifier = modifier.weight(1f)
        )
    }

}