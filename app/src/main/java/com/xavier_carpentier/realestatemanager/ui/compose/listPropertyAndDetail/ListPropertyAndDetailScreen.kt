package com.xavier_carpentier.realestatemanager.ui.compose.listPropertyAndDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.xavier_carpentier.realestatemanager.ui.compose.detail.DetailScreen
import com.xavier_carpentier.realestatemanager.ui.compose.listProperty.ListPropertyScreen

@Composable
fun ListPropertyAndDetailScreen()
{
    ListPropertyAndDetailContent()
}

@Composable
fun ListPropertyAndDetailContent(){
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.weight(0.7f)){
            ListPropertyScreen(onDetailPressButton= {})
        }
        Box(modifier = Modifier.weight(1f)) {
            DetailScreen()
        }
    }
}

@Preview(widthDp = 800, heightDp = 600)
@Preview
@Composable
fun ListPropertyAndDetailScreenPreview(){
    ListPropertyAndDetailContent()
}