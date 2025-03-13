package com.xavier_carpentier.realestatemanager.ui.main

import androidx.annotation.StringRes
import com.xavier_carpentier.realestatemanager.R

sealed class Screen(val route: String, @StringRes val stringRes: Int, val icon: Int,val onMenu :Boolean) {
    data object ListProperty : Screen("properties_list", R.string.menu_property, R.drawable.baseline_home_work_24,true)
    data object Map : Screen("map", R.string.menu_map, R.drawable.baseline_map_24,true)
    data object LoanSimulator : Screen("loan_simulator", R.string.loan_simulator, R.drawable.baseline_price_check_24,true)
    data object Settings : Screen("settings", R.string.menu_setting, R.drawable.baseline_settings_24,true)
    data object CreateAndModifiedProperty : Screen("CreateAndModified/{create}", R.string.create_and_modified_property, R.drawable.baseline_add_home_24,false)
    data object DetailProperty : Screen("detail_property", R.string.detail, R.drawable.baseline_cottage_24,false)
}