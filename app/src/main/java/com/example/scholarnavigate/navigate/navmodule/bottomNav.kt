package com.example.scholarnavigate.navigate.navmodule

import androidx.annotation.DrawableRes
import com.example.scholarnavigate.R

sealed class BottomBarScreen(val route: String, @DrawableRes val icon: Int){
    object Dashboard: BottomBarScreen("Dashboard", R.drawable.dashboard_icon)
    object Calender: BottomBarScreen("Calender", R.drawable.calender)
    object Settings: BottomBarScreen("Settings", R.drawable.settings)
}