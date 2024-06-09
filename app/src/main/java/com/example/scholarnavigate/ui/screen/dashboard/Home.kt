package com.example.scholarnavigate.ui.screen.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.scholarnavigate.navigate.HomeNavGraph
import com.example.scholarnavigate.ui.screen.dashboard.session.SessionTimerService
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController(), timerService: SessionTimerService) {
    ScholarNavigateTheme {
        Scaffold(
            bottomBar = {
                BottomNavGraph(navController = navController)
            }
        ) { paddingValues ->
            Column(
                Modifier.padding(paddingValues)
            ){
                HomeNavGraph(navController, timerService)
            }

        }
    }

}