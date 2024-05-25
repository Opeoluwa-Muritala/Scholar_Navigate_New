package com.example.scholarnavigate.navigate.navmodule

sealed class AuthScreen(val route: String){
    object Login: AuthScreen("login")
    object Splash: AuthScreen("splash")
}

