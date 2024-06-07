package com.example.scholarnavigate.data.database.di

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.example.scholarnavigate.R
import com.example.scholarnavigate.data.util.Constants.NOTIFICATION_CHANNEL_ID
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
            .setOngoing(true)
    }

    @ServiceScoped
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ) : NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}