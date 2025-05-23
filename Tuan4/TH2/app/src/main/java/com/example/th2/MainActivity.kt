package com.example.th2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.th2.ui.theme.TH2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TH2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "list") {
                        composable("list") { ListScreen(navController) }
                        composable("detail/{taskId}") { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull() ?: 0
                            DetailScreen(taskId, navController)
                        }
                    }
                }
            }
        }
    }
}