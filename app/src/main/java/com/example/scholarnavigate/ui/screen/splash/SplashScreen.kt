package com.example.scholarnavigate.ui.screen.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.scholarnavigate.navigate.navmodule.AuthScreen
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme
import com.example.scholarnavigate.ui.theme.primaryDark
import com.example.scholarnavigate.ui.theme.primaryLight
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavController) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            2000
        ), label = ""
    )
    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(1000)
        startAnimation = false
        delay(1000)
        startAnimation = true
        delay(1000)
        navController.navigate(AuthScreen.Login.route)
    }
    Splash(alphaAnim.value)
}

@Composable
fun Splash(alpha:Float) {
    Box(
        modifier = Modifier
            .background(if (isSystemInDarkTheme()) primaryDark else primaryLight)
            .fillMaxSize().alpha(alpha),
        contentAlignment = Alignment.Center
    ){
        Icon(
            modifier = Modifier.size(120.dp),
            imageVector = Icons.Outlined.Search,
            contentDescription = "",
            tint = Color.White
        )
    }
}