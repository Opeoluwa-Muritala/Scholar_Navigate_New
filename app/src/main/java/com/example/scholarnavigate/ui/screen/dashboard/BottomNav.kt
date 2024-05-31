package com.example.scholarnavigate.ui.screen.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.scholarnavigate.navigate.navmodule.BottomBarScreen
import com.example.scholarnavigate.ui.theme.onSecondaryContainerLight

@Composable
fun BottomNavGraph(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Dashboard,
        BottomBarScreen.Calender,
        BottomBarScreen.Settings,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any{ it.route == currentDestination?.route}
    if (bottomBarDestination){
        NavigationBar {
            screens.forEach{screen ->
                NavigationBarItem(selected = currentDestination?.hierarchy?.any{
                    it.route == screen.route
                } == true,
                    onClick = { navController.navigate(screen.route){
                        popUpToRoute
                    } },
                    icon = {
                        Icon(painterResource(id = screen.icon), contentDescription = screen.route)
                    },
                    label = {
                        Text(text = screen.route)
                    },
                )
            }
        }
    }
}

