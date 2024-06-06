package com.example.scholarnavigate.navigate.navmodule

sealed class AuthScreen(val route: String){
    object Login: AuthScreen("login")
    object Splash: AuthScreen("splash")
}

sealed class DashboardScreens(val route:String){
    object SessionScreen: DashboardScreens("session")
    object TaskScreen: DashboardScreens("task")
    object CourseScreen: DashboardScreens("course/{courseId}"){
        fun createCourse(courseId: Int) = "course/$courseId"
    }
}