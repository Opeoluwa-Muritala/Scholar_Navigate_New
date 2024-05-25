package com.example.scholarnavigate.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scholarnavigate.navigate.navmodule.HomeScreen
import com.example.scholarnavigate.ui.screen.dashboard.Dashboard

@Composable
fun HomeNavGraph(navController: NavHostController = rememberNavController()){
    NavHost(navController = navController, route = Graph.HOME , startDestination = HomeScreen.Dashboard.route){
        composable(HomeScreen.Dashboard.route){
            Dashboard()
        }
    }
}