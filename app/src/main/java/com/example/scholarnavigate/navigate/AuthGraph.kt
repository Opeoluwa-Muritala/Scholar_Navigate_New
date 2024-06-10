package com.example.scholarnavigate.navigate

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.scholarnavigate.navigate.navmodule.AuthScreen
import com.example.scholarnavigate.ui.screen.Login.LoginUi
import com.example.scholarnavigate.ui.screen.splash.AnimatedSplashScreen

fun NavGraphBuilder.authNavGraph(navController: NavController){
    navigation(
        startDestination = AuthScreen.Splash.route,
        route = Graph.AUTHENTCATION
    ){
        composable(AuthScreen.Splash.route){
            AnimatedSplashScreen(navController)
        }
        composable(AuthScreen.Login.route){
            LoginUi(navController)
        }
    }
}