package com.example.scholarnavigate

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.scholarnavigate.navigate.RootNavigationGraph
import com.example.scholarnavigate.ui.screen.dashboard.session.SessionTimerService
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var isBound by mutableStateOf(false)
    private lateinit var timerService: SessionTimerService
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as SessionTimerService.StudySessionTimerBinder
            timerService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var hasNotificationPermission by remember {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    mutableStateOf(
                        ContextCompat.checkSelfPermission(
                            applicationContext,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    )
                } else mutableStateOf(true)
            }

            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    hasNotificationPermission = isGranted
                    if (!isGranted){
                        shouldShowRequestPermissionRationale("Kindly Enable the Notification " +
                                "Permission. This is necessary for " +
                                "you to receive todo notifications and for the sessions timer")
                    }

                }
            )

            LaunchedEffect(key1 = true,){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                if (!hasNotificationPermission){
                    Toast.makeText(
                        applicationContext,
                        "Please enable Notification Permission in your settings",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            val navController = rememberNavController()
            if (isBound) {
                ScholarNavigateTheme(darkTheme = isSystemInDarkTheme()) {
                    RootNavigationGraph(navController = navController, timerService)
                }
            } else {
                ScholarNavigateTheme(darkTheme = isSystemInDarkTheme()) {
                    androidx.compose.material3.Surface(modifier = androidx.compose.ui.Modifier.fillMaxSize()) {}
                }
            }

        }
    }
    override fun onStart(){
        super.onStart()
        Intent(this, SessionTimerService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }
    override fun onStop(){
        super.onStop()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }

}