package com.milkymo.milky_mo.navigation

import com.milkymo.milky_mo.features.auth.login.LoginScreen
import com.milkymo.milky_mo.features.auth.login.LoginViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.milkymo.milky_mo.features.home.HomeScreen
import com.milkymo.milky_mo.features.home.HomeViewModel

fun NavGraphBuilder.AuthNavGraph(
    navController: NavHostController,
    isLogin: Boolean
){
    navigation(
        route = AppScreen.Auth.route,
        startDestination = AppScreen.Auth.Login.route,
    ){

        composable(
            route = AppScreen.Auth.Login.route
        ){
           val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                loginViewModel = viewModel,
                isLogin = isLogin,
                navigateToHome = {
                    navController.navigate(AppScreen.Main.route){
                        popUpTo(AppScreen.Auth.route){
                            inclusive = true
                        }
                    }
                }
            )
        }

        // another navigation here ...
    }
}