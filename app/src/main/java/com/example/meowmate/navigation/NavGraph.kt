package com.example.meowmate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.meowmate.ui.screens.CatDetailsScreen
import com.example.meowmate.ui.screens.CatsListScreen
import com.example.meowmate.ui.screens.SettingsScreen

object Routes {
    const val LIST = "list"
    const val DETAIL = "detail/{imageId}"

    const val SETTINGS = "settings"
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            CatsListScreen(
                onOpenDetails = { id ->
                    navController.navigate("detail/$id") {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(Routes.LIST) { saveState = true; inclusive = false }
                    }
                },
                onOpenSettings = {
                    navController.navigate(Routes.SETTINGS) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(Routes.LIST) { saveState = true; inclusive = false }
                    }
                }
            )
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("imageId") { type = NavType.StringType })
        ) { backStack ->
            val imageId = backStack.arguments?.getString("imageId")!!
            CatDetailsScreen(
                imageId = imageId,
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Routes.SETTINGS,
        ) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}
