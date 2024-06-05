package com.example.scholarnavigate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.module.Session
import com.example.scholarnavigate.data.module.Task
import com.example.scholarnavigate.navigate.RootNavigationGraph
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScholarNavigateTheme(darkTheme = isSystemInDarkTheme()) {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}





