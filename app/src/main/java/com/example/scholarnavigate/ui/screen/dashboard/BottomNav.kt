package com.example.scholarnavigate.ui.screen.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.scholarnavigate.navigate.navmodule.BottomBarScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Dashboard,
        BottomBarScreen.Calendar,
    )
    var selectedScreen by remember {
        mutableStateOf<BottomBarScreen>(BottomBarScreen.Dashboard)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    if (screens.any{it.route == currentDestination?.route} ) {
        NavigationBar {
            screens.forEachIndexed{index, screen ->
                NavigationBarItem(
                    selected = screens[index].route == selectedScreen.route,
                    onClick = {
                        selectedScreen = screen
                        navController.navigate(screen.route)
                    },
                    icon = {
                        Image(
                            painterResource(id = screen.icon),
                            contentDescription = screen.route,
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    label = {
                        Text(text = screen.route)
                    })
            }
        }
    }
}
