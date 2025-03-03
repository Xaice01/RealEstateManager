package com.xavier_carpentier.realestatemanager.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.xavier_carpentier.realestatemanager.ui.compose.navigation.NavigationScreen
import com.xavier_carpentier.realestatemanager.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(){
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        setContent{
            AppTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                NavigationScreen(viewModel, windowSizeClass)
            }
        }

    }

}