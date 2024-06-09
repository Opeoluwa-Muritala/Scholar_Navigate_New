package com.example.scholarnavigate.ui.screen.dashboard.session

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.example.scholarnavigate.MainActivity
import com.example.scholarnavigate.data.util.Constants.CLICK_NOTIFICATION_CODE
import com.example.scholarnavigate.navigate.navmodule.DashboardScreens

object ServiceHelper {

    fun clickPendingIntent(context: Context): PendingIntent {
        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            "scholarnavigate://example.com/${DashboardScreens.SessionScreen.route}".toUri(),
            context,
            MainActivity::class.java
        )
        return TaskStackBuilder.create(context). run {
            addNextIntentWithParentStack(deepLinkIntent)
            getPendingIntent(
                CLICK_NOTIFICATION_CODE,
                PendingIntent.FLAG_IMMUTABLE
            )
        }
    }
    fun triggerForeGroundService(context: Context, action: String){
        context.startService(
            Intent(context, SessionTimerService::class.java).apply {
            this.action = action
            context.stopService(this)
        })

    }
}