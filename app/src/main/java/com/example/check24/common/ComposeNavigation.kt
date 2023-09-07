package com.example.check24.common

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.check24.details.presentation.DetailsScreen
import com.example.check24.overview.presentation.ProductOverview

@Composable
fun ComposeNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Overview.route) {
        composable(Screen.Overview.route) {
            ProductOverview(navController)
        }
        composable(Screen.Details.route) {
            DetailsScreen(navController)
        }

    }


}