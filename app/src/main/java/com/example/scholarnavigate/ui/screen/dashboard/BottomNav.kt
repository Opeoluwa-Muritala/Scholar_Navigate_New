package com.example.scholarnavigate.ui.screen.dashboard

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
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
    Log.d("bottomBarDestination result", "$bottomBarDestination")
    Log.d("bottomBarDestination result", "${currentDestination?.route}")
    if (true){
        BottomAppBar {
            screens.forEach{screen ->
                AddItems(currentDestination = currentDestination, screen = screen, navController = navController)

            }
        }
    }
}

@Composable
fun RowScope.AddItems(
    currentDestination: NavDestination?,
    screen: BottomBarScreen,
    navController: NavHostController
) {


        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
            onClick = { navController.navigate(screen.route) },
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
