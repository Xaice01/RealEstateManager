package com.xavier_carpentier.realestatemanager.ui.compose.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailSection(
    title: String,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier) {
        Text(
            text = title,
            modifier
                .paddingFromBaseline(top = 40.dp, bottom = 8.dp)
                .padding(start = 16.dp),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}