package com.example.scholarnavigate.ui.screen.dashboard

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.scholarnavigate.navigate.navmodule.BottomBarScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Dashboard,
        BottomBarScreen.Calender,
        BottomBarScreen.Settings,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    if (true){
        NavigationBar {
            screens.forEachIndexed{index, screen ->
                NavigationBarItem(
                    selected = screens[index] == selectedScreen,
                    onClick = { navController.navigate(screen.route)
                        selectedScreen = screen
                              },
                    icon = {
                        Icon(
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

@Composable
fun RowScope.AddItems(
    screen: BottomBarScreen,
    navController: NavHostController,
    selectedScreen: BottomBarScreen
) {



}
