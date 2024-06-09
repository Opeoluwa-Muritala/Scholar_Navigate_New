package com.example.scholarnavigate.data.database.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.scholarnavigate.R
import com.example.scholarnavigate.data.util.Constants
import com.example.scholarnavigate.data.util.Constants.NOTIFICATION_CHANNEL_ID
import com.example.scholarnavigate.ui.screen.dashboard.session.ServiceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object NotificationModule {

    @ServiceScoped
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context

    ) : NotificationCompat.Builder {
        return NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Scholar Navigate")
            .setContentText("00:00:00")
            .setSmallIcon(R.drawable.applogo)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(ServiceHelper.clickPendingIntent(context))
            .setOngoing(true)
    }

    @ServiceScoped
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ) : NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    Constants.TIMER_CHANNEL_ID,
                    Constants.TIMER_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            notificationManager.createNotificationChannel(channel)
            }
        return notificationManager
    }
}