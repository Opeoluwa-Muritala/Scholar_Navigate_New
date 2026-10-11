package com.example.scholarnavigate.navigate.navmodule

import androidx.annotation.DrawableRes
import com.example.scholarnavigate.R

sealed class BottomBarScreen(val route: String, @DrawableRes val icon: Int){
    object Dashboard: BottomBarScreen("Dashboard", R.drawable.outline_home_24)
    object Calendar: BottomBarScreen("Calendar", R.drawable.calender)
}