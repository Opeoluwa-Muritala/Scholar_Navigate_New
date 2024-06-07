package com.example.scholarnavigate.ui.screen.dashboard.session

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import com.example.scholarnavigate.data.util.Constants.ACTION_SERVICE_CANCEL
import com.example.scholarnavigate.data.util.Constants.ACTION_SERVICE_START
import com.example.scholarnavigate.data.util.Constants.ACTION_SERVICE_STOP
import com.example.scholarnavigate.data.util.Constants.NOTIFICATION_CHANNEL_ID
import com.example.scholarnavigate.data.util.Constants.NOTIFICATION_CHANNEL_NAME
import com.example.scholarnavigate.data.util.Constants.NOTIFICATION_ID
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.util.Timer
import javax.inject.Inject

@AndroidEntryPoint
class SessionTimerService: Service() {

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var timer: Timer
    var duration: Duration = Duration.ZERO
        private set
    var seconds = mutableStateOf("00")
        private set
    var minutes = mutableStateOf("00")
        private set
    var hours = mutableStateOf("00")
        private set
    val currentState = = mutableStateOf(TimerState.)

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action.let {
            when (it) {
                ACTION_SERVICE_START -> {

                }

                ACTION_SERVICE_STOP -> {

                }

                ACTION_SERVICE_CANCEL -> {

                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("ForegroundServiceType")
    private fun startForegroundService() {
        createNotificationChannel()
        startForeground(
            NOTIFICATION_ID, notificationBuilder.build()
        )
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW
                )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun StartTimer(
        onTick: (h: String, m: String, s: String) -> Unit
    ) {

    }

    enum class TimerState(
        IDLE,
        STARTED
        STOPPED
    )
}

