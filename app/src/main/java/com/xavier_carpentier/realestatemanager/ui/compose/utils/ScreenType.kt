package com.xavier_carpentier.realestatemanager.ui.compose.utils
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable

enum class ScreenType { Compact, Medium, Expanded }

@Composable
fun getScreenType(windowSizeClass: WindowSizeClass): ScreenType {
    return when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> ScreenType.Compact
        WindowWidthSizeClass.Medium -> ScreenType.Medium
        WindowWidthSizeClass.Expanded ->{
            if (windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact)
                ScreenType.Medium
            else
                ScreenType.Expanded
        }
        else -> ScreenType.Compact  // default value
    }
}