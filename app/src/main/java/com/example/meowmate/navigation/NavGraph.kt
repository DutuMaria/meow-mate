package com.example.meowmate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.meowmate.ui.CatDetailsScreen
import com.example.meowmate.ui.CatsListScreen

object Routes {
    const val LIST = "list"
    const val DETAIL = "detail/{imageId}"
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            CatsListScreen(
                onOpenDetails = { id -> navController.navigate("detail/$id") }
            )
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("imageId") { type = NavType.StringType })
        ) { backStack ->
            val imageId = backStack.arguments?.getString("imageId")!!
            CatDetailsScreen(imageId = imageId, onBack = { navController.popBackStack() })
        }
    }
}
