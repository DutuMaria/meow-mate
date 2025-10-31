package com.example.meowmate.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.meowmate.ui.CatDetailViewModel
import com.example.meowmate.ui.CatsViewModel

object Routes {
    const val LIST = "list"
    const val DETAIL = "detail/{imageId}"
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            val vm: CatsViewModel = hiltViewModel()
            Text("List screen placeholder; items: ${vm.state.collectAsState().value.items.size}")
        }
        composable(Routes.DETAIL) { backStackEntry ->
            val vm: CatDetailViewModel = hiltViewModel()
            Text(text = "Detail screen placeholder")
        }
    }
}
