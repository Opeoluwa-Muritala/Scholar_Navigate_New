package com.example.scholarnavigate.ui.screen.dashboard.session

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.scholarnavigate.data.util.Constants.ACTION_SERVICE_CANCEL
import com.example.scholarnavigate.data.util.Constants.ACTION_SERVICE_START
import com.example.scholarnavigate.data.util.Constants.ACTION_SERVICE_STOP
import com.example.scholarnavigate.data.util.Constants.TIMER_CHANNEL_ID
import com.example.scholarnavigate.data.util.Constants.TIMER_CHANNEL_NAME
import com.example.scholarnavigate.data.util.Constants.TIMER_ID
import com.example.scholarnavigate.data.util.pad
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class SessionTimerService: Service() {

    val binder = StudySessionTimerBinder()

    @Inject
    lateinit var notificationManager: NotificationManagerCompat

    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder
    
    private lateinit var timer: Timer
    var duration: Duration = ZERO
        private set
    var second = mutableStateOf("00")
        private set
    var minutes = mutableStateOf("00")
        private set
    var hours = mutableStateOf("00")
        private set
    var currentState =  mutableStateOf(TimerState.IDLE)
        private set

    var courseId = mutableStateOf<Int?>(null)

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action.let {
            when (it) {
                ACTION_SERVICE_START -> {
                    startForegroundService()
                    startTimer { hours, minutes, seconds ->
                        updateNotification(hours,minutes,seconds)
                    }
                }

                ACTION_SERVICE_STOP -> {
                    stopTimer()
                }

                ACTION_SERVICE_CANCEL -> {
                    stopTimer()
                    cancelTimer()
                    stopForegroundService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("ForegroundServiceType")
    private fun startForegroundService() {
        createNotificationChannel()
        startForeground(
            TIMER_ID, notificationBuilder.build()
        )
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    TIMER_CHANNEL_ID,
                    TIMER_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateNotification(h: String, m: String, s: String){
        notificationManager.notify(
            TIMER_ID,
            notificationBuilder
                .setContentText("$h:$m:$s")
                .build()
        )
    }

    private fun stopForegroundService(){
        notificationManager.cancel(TIMER_ID)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }
    private fun startTimer(
        onTick: (h: String, m: String, s: String) -> Unit
    ) {
        currentState.value = TimerState.STARTED
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L){
            duration = duration.plus(1.seconds)
            updateTimeMillis()
            onTick(hours.value,minutes.value,second.value)
        }
    }

    private fun stopTimer() {
        if (this::timer.isInitialized){
            timer.cancel()
             timer.purge()
        }
        currentState.value = TimerState.STOPPED
    }

    private fun cancelTimer(){
        duration = ZERO
        updateTimeMillis()
        currentState.value = TimerState.IDLE
    }

    private fun updateTimeMillis(){
        duration.toComponents{ hours, minutes, seconds, _ ->
            this@SessionTimerService.hours.value = hours.toInt().pad()
            this@SessionTimerService.minutes.value = minutes.pad()
            this@SessionTimerService.second.value = seconds.pad()
        }
    }

    inner class StudySessionTimerBinder: Binder() {
        fun getService(): SessionTimerService = this@SessionTimerService
    }

}

enum class TimerState{
    IDLE,
    STARTED,
    STOPPED
}