package com.example.scholarnavigate.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.scholarnavigate.ui.screen.dashboard.HomeScreen
import com.example.scholarnavigate.ui.screen.dashboard.session.SessionTimerService

@Composable
fun RootNavigationGraph(navController: NavHostController, timerService: SessionTimerService) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTCATION
    ){
        authNavGraph(navController)
        composable(Graph.HOME){
            HomeScreen(timerService = timerService)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTCATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
}