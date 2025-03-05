package com.xavier_carpentier.realestatemanager.ui.compose.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.xavier_carpentier.realestatemanager.R
import com.xavier_carpentier.realestatemanager.ui.compose.createAndModified.CreateAndModifiedScreen
import com.xavier_carpentier.realestatemanager.ui.compose.detail.DetailScreen
import com.xavier_carpentier.realestatemanager.ui.compose.filtre.FilterScreen
import com.xavier_carpentier.realestatemanager.ui.compose.listProperty.ListPropertyScreen
import com.xavier_carpentier.realestatemanager.ui.compose.listPropertyAndDetail.ListPropertyAndDetailScreen
import com.xavier_carpentier.realestatemanager.ui.compose.loanSimulator.LoanSimulatorScreen
import com.xavier_carpentier.realestatemanager.ui.compose.map.MapScreen
import com.xavier_carpentier.realestatemanager.ui.compose.setting.SettingScreen
import com.xavier_carpentier.realestatemanager.ui.main.MainViewModel
import com.xavier_carpentier.realestatemanager.ui.main.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScreen(viewModel: MainViewModel = hiltViewModel(), windowSizeClass : WindowSizeClass){

    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    var showDialog by remember{mutableStateOf(false)}
    val isFilter by viewModel.isFilter.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                colors= TopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer,scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer, navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer),
                title = { Text("Real Estate Manager") },
                actions = {
                    IconButton(onClick = { navController.navigate("CreateAndModified/true") }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_add_home_24),
                            contentDescription = "Add Property"
                        )
                    }
                    AnimatedVisibility(
                        visible = currentDestination?.route == Screen.DetailProperty.route,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = { navController.navigate("CreateAndModified/false") }) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_edit_24),
                                contentDescription = "Edit Property"
                            )
                        }
                    }

                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            painter= if(isFilter){
                                painterResource(R.drawable.baseline_filter_list_off_24)
                            }else{
                                painterResource(R.drawable.baseline_filter_list_24)
                            },
                            contentDescription = "Filter"
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                listOf(Screen.ListProperty, Screen.Map, Screen.LoanSimulator, Screen.Settings).forEach { screen ->
                    NavigationBarItem(
                        selected = currentDestination?.route == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(painter = painterResource(screen.icon), contentDescription = stringResource(screen.stringRes)) },
                        label = { Text(stringResource(screen.stringRes)) }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.ListProperty.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.ListProperty.route) {
                if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact){
                    ListPropertyScreen(
                        onDetailPressButton = { navController.navigate(Screen.DetailProperty.route) }
                    )
                }else {
                    ListPropertyAndDetailScreen()
                }
            }
            composable(Screen.Map.route) {
                MapScreen(
                    onDetailPressButton = { navController.navigate(Screen.DetailProperty.route) }
                )
            }
            composable(Screen.LoanSimulator.route) {
                LoanSimulatorScreen()
            }
            composable(Screen.Settings.route) {
                SettingScreen()
            }

            composable(
                route = Screen.CreateAndModifiedProperty.route,
                arguments = listOf(navArgument("create") { type = NavType.BoolType })
            ){ backStackEntry ->
                CreateAndModifiedScreen(
                    create = backStackEntry.arguments?.getBoolean("create") ?: true,
                    onBackNavigation={navController.popBackStack()}
                )
            }
            composable(Screen.DetailProperty.route) {
                if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact){
                    DetailScreen()
                }else {
                    ListPropertyAndDetailScreen()
                }
            }
        }
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxSize(0.98f),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.background
            ) {
                FilterScreen(
                    onFilterApplied = {
                        showDialog = false
                    }
                )
            }
        }
    }

}