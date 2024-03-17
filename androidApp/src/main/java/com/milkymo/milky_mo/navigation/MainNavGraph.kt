package com.milkymo.milky_mo.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.milkymo.milky_mo.features.home.HomeScreen
import com.milkymo.milky_mo.features.home.HomeViewModel
import com.milkymo.milky_mo.features.home.HomeViewModel.HomeUiState
import com.milkymo.milky_mo.features.home.HomeViewModel.HomeUiState.Logout
import kotlinx.coroutines.delay

fun NavGraphBuilder.MainNavGraph(
    navController: NavHostController,
    rootNavBackStackEntry: NavBackStackEntry?
){
    navigation(
        route = AppScreen.Main.route,
        startDestination = AppScreen.Main.Home.route,
    ){
        composable(
            route = AppScreen.Main.Home.route,
            enterTransition = {
                return@composable fadeIn(tween(1000))
            },
            exitTransition = {
                return@composable fadeOut(tween(700))
            },
        ){
            val viewModel: HomeViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            HomeScreen(
                viewModel = viewModel,
                onLogoutClicked = {
                    viewModel.logout()
                    if (state is Logout){
                        navController.navigate(AppScreen.Auth.route){
                            popUpTo(AppScreen.Main.route){
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }

        // another navigation here ...
    }
}