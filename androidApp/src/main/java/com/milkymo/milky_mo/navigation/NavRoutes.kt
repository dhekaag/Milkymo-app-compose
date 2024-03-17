package com.milkymo.milky_mo.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument


private object Routes {
    // First Graph Route
    const val AUTH = "auth"
    const val LOGIN = "login"
    const val FORGOT_ACCOUNT = "f orgot-account"
    const val OTP = "otp"
    const val CREATE_NEW_PASSWORD = "create-new-password"

    // Second Graph Route
    const val MAIN = "main"
    const val HOME = "home"
    const val PROFILE = "profile"
    const val NOTIFICATION = "notification"
//    const val TRANSACTION_DETAIL = "productDetail/{${ArgParams.PRODUCT_ID}}"

}



sealed class AppScreen(internal open val route: String) {

    object Auth : AppScreen(Routes.AUTH){
        object Login : AppScreen(Routes.LOGIN)
        object ForgotAccount : AppScreen(Routes.FORGOT_ACCOUNT)
        object Otp : AppScreen(Routes.OTP)
        object CreateNewPassword : AppScreen(Routes.CREATE_NEW_PASSWORD)
    }


    object Main : AppScreen(Routes.MAIN){
        object Home : AppScreen(Routes.HOME)

    }

    object List : AppScreen("/list")
    object Details : AppScreen("/details/{$DETAILS_ID_KEY}") {
        fun build(id: String): String =
            route.replace("{$DETAILS_ID_KEY}", id)
    }




    companion object {
        const val DETAILS_ID_KEY: String = "id"
    }

}



sealed class TopLevelDestination(
    val route: String,
    val title: Int? = null,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    val navArguments: List<NamedNavArgument> = emptyList()
)
