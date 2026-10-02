package com.example.scholarnavigate.ui.screen.splash

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.firebaseauthyt.presentation.login_screen.SignInViewModel
import com.example.scholarnavigate.MainActivity
import com.example.scholarnavigate.R
import com.example.scholarnavigate.data.util.Constants
import com.example.scholarnavigate.data.util.Constants.NOTIFICATION_ID
import com.example.scholarnavigate.navigate.Graph
import com.example.scholarnavigate.navigate.navmodule.AuthScreen
import com.example.scholarnavigate.navigate.navmodule.BottomBarScreen
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme
import com.example.scholarnavigate.ui.theme.primaryLight
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavController) {
    val viewModel: SignInViewModel = hiltViewModel()
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            2000
        ), label = ""
    )
    val context =LocalContext.current

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(500)
        startAnimation = false
        delay(500)
        startAnimation = true
        delay(2000)

        showNotification(context = context)
        val isLoggedIn = viewModel.isUserLoggedIn()

        val destination = if (isLoggedIn) Graph.HOME else AuthScreen.Login.route
        navController.navigate(destination) {
            popUpTo(AuthScreen.Splash.route) { inclusive = true }
        }
    }
    Splash(alphaAnim.value)
}


private fun showNotification(context: Context){

    val notificationManager = NotificationManagerCompat.from(context)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel =
            NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                Constants.NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
        notificationManager.createNotificationChannel(channel)
    }
    val notification = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
        .setContentTitle("Welcome To Scholar Navigate")
        .setContentText("Bring Out The Scholar Within You With A Study Session")
        .setSmallIcon(R.drawable.applogo)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(clickPendingIntent(context))
        .build()

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }
    notificationManager.notify(NOTIFICATION_ID, notification)
}


private fun clickPendingIntent(context: Context): PendingIntent {
    val deepLinkIntent = Intent(
        Intent.ACTION_VIEW,
        "scholarnavigate://example.com/${BottomBarScreen.Dashboard.route}".toUri(),
        context,
        MainActivity::class.java
    )
    return TaskStackBuilder.create(context). run {
        addNextIntentWithParentStack(deepLinkIntent)
        getPendingIntent(
            Constants.CLICK_NOTIFICATION_CODE,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}

@Composable
fun Splash(alpha:Float) {
    ScholarNavigateTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (isSystemInDarkTheme()) primaryLight else MaterialTheme.colorScheme.inversePrimary
                ),
            contentAlignment = Alignment.Center
        ){
            Image(
                modifier = Modifier
                    .size(300.dp)
                    .alpha(alpha)
                ,
                painter = painterResource(id = R.drawable.applogo),
                contentDescription = "Scholar Navigate",
                colorFilter = ColorFilter.tint( if (isSystemInDarkTheme()) Color.White else Color.Blue)
            )
        }
    }

}