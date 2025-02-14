package com.xavier_carpentier.realestatemanager.ui.compose.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.xavier_carpentier.realestatemanager.ui.compose.listProperty.ListPropertyScreen
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Real Estate Manager") },
                actions = {
                    IconButton(onClick = { navController.navigate("CreateAndModified/true") }) {
                        Icon(painter = painterResource(R.drawable.baseline_add_home_24), contentDescription = "Add Property")
                    }
                    IconButton(onClick = { showDialog = true }) {
                        Icon(painter = painterResource(R.drawable.baseline_settings_24), contentDescription = "Settings")
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
                    //ListPropertyScreenAndDetail(
                    //    onModifyPressButton = { navController.navigate("CreateAndModified/false") }
                    //)
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

            //navController.navigate("Details?name=$nameValue&age=$ageValue")  Home -> Details
            composable(
                route = Screen.CreateAndModifiedProperty.route,
                arguments = listOf(navArgument("create") { type = NavType.BoolType })
            ){ backStackEntry ->
                CreateAndModifiedScreen(
                    create = backStackEntry.arguments?.getBoolean("create") ?: true
                )
            }
            composable(Screen.DetailProperty.route) {
                if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact){
                    DetailScreen(
                        onModifyPressButton = { navController.navigate("CreateAndModified/false") }
                    )
                }else {
                    //ListPropertyScreenAndDetail(
                    //    onModifyPressButton = { navController.navigate("CreateAndModified/false") }
                    //)
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Settings") },
            text = { Text("This is a settings dialog.") },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }


    //NavHost(
    //    navController = navController,
    //    startDestination = Screen.ListProperty.route
    //) {
    //    composable(Screen.ListProperty.route) {
    //        if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact){
    //            ListPropertyScreen(
    //                onDetailPressButton = { navController.navigate(Screen.DetailProperty.route) }
    //            )
    //        }else {
    //            //ListPropertyScreenAndDetail(
    //            //    onModifyPressButton = { navController.navigate("CreateAndModified/false") }
    //            //)
    //        }
    //    }
    //    composable(Screen.Map.route) {
    //        MapScreen(
    //            onDetailPressButton = { navController.navigate(Screen.DetailProperty.route) }
    //        )
    //    }
    //    composable(Screen.LoanSimulator.route) {
    //        LoanSimulatorScreen()
    //    }
    //    composable(Screen.Settings.route) {
    //        SettingScreen()
    //    }
//
    //    //navController.navigate("Details?name=$nameValue&age=$ageValue")  Home -> Details
    //    composable(
    //        route = Screen.CreateAndModifiedProperty.route,
    //        arguments = listOf(navArgument("create") { type = NavType.BoolType })
    //    ){ backStackEntry ->
    //        CreateAndModifiedScreen(
    //            create = backStackEntry.arguments?.getBoolean("create") ?: true
    //        )
    //    }
    //    composable(Screen.DetailProperty.route) {
    //        if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact){
    //            DetailScreen(
    //                onModifyPressButton = { navController.navigate("CreateAndModified/false") }
    //            )
    //        }else {
    //            //ListPropertyScreenAndDetail(
    //            //    onModifyPressButton = { navController.navigate("CreateAndModified/false") }
    //            //)
    //        }
    //    }
    //}
    //val screens = listOf(
    //    Screen.ListProperty,
    //    Screen.Map,
    //    Screen.LoanSimulator,
    //    Screen.Settings
    //)

   // Scaffold(
   //     bottomBar = {
//
   //         }
   //     }
//
   // )

    //NavigationSuiteScaffold(
    //    // Barre de navigation latérale
    //    navRailContent = {
    //        NavigationRail {
    //            screens.forEach { screen ->
    //                NavigationRailItem(
    //                    selected = navController.currentBackStackEntry?.destination?.route == screen.route,
    //                    onClick = {
    //                        navController.navigate(screen.route) {
    //                            popUpTo(navController.graph.startDestinationId) {
    //                                saveState = true
    //                            }
    //                            launchSingleTop = true
    //                            restoreState = true
    //                        }
    //                    },
    //                    icon = {
    //                        Icon(
    //                            painter = painterResource(screen.icon),
    //                            contentDescription = stringResource(screen.stringRes)
    //                        )
    //                    },
    //                    label = { Text(stringResource(screen.stringRes)) }
    //                )
    //            }
    //        }
    //    }
    //)
}