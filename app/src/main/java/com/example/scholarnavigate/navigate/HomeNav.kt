package com.example.scholarnavigate.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.scholarnavigate.navigate.navmodule.BottomBarScreen
import com.example.scholarnavigate.navigate.navmodule.DashboardScreens
import com.example.scholarnavigate.ui.screen.calendar.CalenderUi
import com.example.scholarnavigate.ui.screen.dashboard.DashboardScreenNavigation
import com.example.scholarnavigate.ui.screen.dashboard.courseinfo.CourseScreenNavigation
import com.example.scholarnavigate.ui.screen.dashboard.createTask.TaskScreenNavigation
import com.example.scholarnavigate.ui.screen.dashboard.session.SessionScreenNavigation
import com.example.scholarnavigate.ui.screen.dashboard.session.SessionTimerService

@Composable
fun HomeNavGraph(navController: NavHostController = rememberNavController(), timerService: SessionTimerService){
    NavHost(navController = navController, route = Graph.HOME , startDestination = BottomBarScreen.Dashboard.route){
        composable(BottomBarScreen.Dashboard.route,
            deepLinks = listOf(
                navDeepLink { uriPattern =  "scholarnavigate://example.com/${BottomBarScreen.Dashboard.route}"}
            )
        ){
            DashboardScreenNavigation(navController)
        }
        composable(BottomBarScreen.Calendar.route){
            CalenderUi(navController)
        }
        composable(DashboardScreens.CourseScreen.route,
            arguments = listOf(
                navArgument("courseId"){
                    type = NavType.IntType
                }
            )
        ){
            CourseScreenNavigation(navController)

        }
        composable(DashboardScreens.SessionScreen.route,
            deepLinks = listOf(
                navDeepLink { uriPattern =  "scholarnavigate://example.com/${DashboardScreens.SessionScreen.route}" }
            )){
            SessionScreenNavigation(navController, timerService)
        }
        composable(DashboardScreens.TaskScreen.route,
            arguments = listOf(
                navArgument("taskId"){
                    type = NavType.IntType
                },
                navArgument("courseId"){
                    type = NavType.IntType
                }
            ),

            ){
            TaskScreenNavigation(navController)
        }

    }
}

