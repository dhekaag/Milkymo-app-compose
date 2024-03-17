package com.milkymo.milky_mo.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.milkymo.milky_mo.features.auth.login.LoginScreen
import com.milkymo.milky_mo.features.auth.login.LoginViewModel
import com.milkymo.milky_mo.features.details.DetailsScreen
import com.milkymo.milky_mo.features.list.ListScreen



@Composable
fun milkymoNavHost(
    navController: NavHostController = rememberNavController(),
    userLoggedIn: Boolean
) {

    val topAppbarTitle = remember { mutableStateOf("") }
    val topAppBarState = rememberTopAppBarState()
    val barScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = topAppBarState)
    val snackbarHostState = remember { SnackbarHostState() }
    val showBottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val showTopBarState = rememberSaveable { (mutableStateOf(true)) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var drawerWidth by remember {
        mutableFloatStateOf(drawerState.offset.value)
    }
    val contentOffset = remember {
        derivedStateOf {
            drawerState.offset.value
        }
    }
    // State composable used to hold the
    // value of the current active screen
    val currentScreen = remember { mutableStateOf(AppScreen.Main.Home.route) }
    val coroutineScope = rememberCoroutineScope()
    val rootNavHostController = rememberNavController()
    val rootNavBackStackEntry by rootNavHostController.currentBackStackEntryAsState()


    NavHost(
        navController = navController,
        startDestination = if (userLoggedIn) AppScreen.Main.route else AppScreen.Auth.route ,
        enterTransition = {
            // you can change whatever you want transition
            EnterTransition.None
        },
        exitTransition = {
            // you can change whatever you want transition
            ExitTransition.None
        }
    ) {

        MainNavGraph(navController, rootNavBackStackEntry)
        AuthNavGraph(navController, userLoggedIn)

        // starter kit
        composable(AppScreen.List.route) {
            ListScreen(hiltViewModel(), openDetailsClicked = {
                navController.navigate(AppScreen.Details.build(it))
            })
        }
        composable(AppScreen.Details.route) { backStackEntry ->
            backStackEntry.arguments?.getString(AppScreen.DETAILS_ID_KEY)?.let {
                DetailsScreen(detailViewModel(bookId = it))
            }
        }
    }
}
