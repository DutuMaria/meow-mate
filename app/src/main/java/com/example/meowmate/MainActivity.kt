package com.example.meowmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.meowmate.navigation.AppNavHost
import com.example.meowmate.ui.theme.MeowMateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeowMateTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                }
                Surface {
                    val navController = rememberNavController()
                    AppNavHost(navController)
                }
            }
        }
    }
}
