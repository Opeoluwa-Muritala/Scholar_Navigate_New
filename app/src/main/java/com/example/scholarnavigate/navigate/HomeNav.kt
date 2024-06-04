package com.example.scholarnavigate.navigate

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.scholarnavigate.navigate.navmodule.BottomBarScreen
import com.example.scholarnavigate.navigate.navmodule.DashboardScreens
import com.example.scholarnavigate.ui.screen.dashboard.DashboardScreenNavigation
import com.example.scholarnavigate.ui.screen.dashboard.courseinfo.CourseScreenNavigation
import com.example.scholarnavigate.ui.screen.dashboard.createTask.TaskScreenNavigation
import com.example.scholarnavigate.ui.screen.dashboard.session.SessionScreenNavigation

@Composable
fun HomeNavGraph(navController: NavHostController = rememberNavController()){
    NavHost(navController = navController, route = Graph.HOME , startDestination = BottomBarScreen.Dashboard.route){
        composable(BottomBarScreen.Dashboard.route){
            DashboardScreenNavigation(navController)
        }
        composable(BottomBarScreen.Settings.route){
            Text(text = "Settings")
        }
        composable(BottomBarScreen.Calender.route){
            Text(text = "Calendar")
        }
        dashboardScreen(navController)

    }
}

fun NavGraphBuilder.dashboardScreen(navController: NavHostController){
    navigation(route = Graph.DETAILS, startDestination = DashboardScreens.CourseScreen.route){
        composable("${DashboardScreens.CourseScreen.route}/{courseId}",
            arguments = listOf(
                navArgument("courseId"){
                    type = NavType.IntType
                }
            )
        ){backStackEntry->
            val courseId = backStackEntry.arguments?.getInt("courseId")
            Log.d("navigation", "Course Id: $courseId")
            if (courseId != null) {
                CourseScreenNavigation(navController, courseId)
            }
        }
        composable(DashboardScreens.SessionScreen.route){
            SessionScreenNavigation(navController)
        }
        composable(DashboardScreens.TaskScreen.route){
            TaskScreenNavigation(navController)
        }
    }

}