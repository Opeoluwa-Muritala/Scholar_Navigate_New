package com.example.scholarnavigate.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scholarnavigate.navigate.navmodule.BottomBarScreen
import com.example.scholarnavigate.navigate.navmodule.DashboardScreens
import com.example.scholarnavigate.ui.screen.dashboard.DashboardScreen
import com.example.scholarnavigate.ui.screen.dashboard.DashboardScreenNavigation
import com.example.scholarnavigate.ui.screen.dashboard.courseinfo.CourseScreenNavigation
import com.example.scholarnavigate.ui.screen.dashboard.createTask.TaskScreenNavigation
import com.example.scholarnavigate.ui.screen.dashboard.session.SessionScreenNavigation

@Composable
fun HomeNavGraph(navController: NavHostController = rememberNavController()){
    NavHost(navController = navController, route = Graph.HOME , startDestination = BottomBarScreen.Dashboard.route){
        composable(BottomBarScreen.Dashboard.route){
            DashboardScreenNavigation()
        }
        composable(BottomBarScreen.Settings.route){

        }
        composable(BottomBarScreen.Calender.route){

        }
        composable(BottomBarScreen.Resources.route){

        }
    }
}

fun NavGraphBuilder.dashboardScreen(navController: NavHostController){
    composable(DashboardScreens.CourseScreen.route){
        CourseScreenNavigation()
    }
    composable(DashboardScreens.SessionScreen.route){
        SessionScreenNavigation()
    }
    composable(DashboardScreens.TaskScreen.route){
        TaskScreenNavigation()
    }
}