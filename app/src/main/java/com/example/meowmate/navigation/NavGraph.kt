package com.example.meowmate.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        composable(Routes.DETAIL) {
            Text(text = "Detail screen placeholder")
        }
    }
}
