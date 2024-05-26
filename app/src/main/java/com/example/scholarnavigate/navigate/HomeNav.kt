package com.example.scholarnavigate.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scholarnavigate.navigate.navmodule.BottomBarScreen
import com.example.scholarnavigate.ui.screen.dashboard.DashboardScreen

@Composable
fun HomeNavGraph(navController: NavHostController = rememberNavController()){
    NavHost(navController = navController, route = Graph.HOME , startDestination = BottomBarScreen.Dashboard.route){
        composable(BottomBarScreen.Dashboard.route){
            DashboardScreen()
        }
        composable(BottomBarScreen.Settings.route){

        }
        composable(BottomBarScreen.Calender.route){

        }
        composable(BottomBarScreen.Resources.route){

        }
    }
}