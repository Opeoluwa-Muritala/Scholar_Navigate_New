package com.example.scholarnavigate.ui.screen.dashboard.session

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.scholarnavigate.R
import com.example.scholarnavigate.data.util.Constants

object ServiceHelper {
    fun triggerForeGroundService(context: Context, action: String){
        context.startService(
            Intent(context, SessionTimerService::class.java).apply {
            this.action = action
            context.stopService(this)
        })

    }
}

//private fun showNotification(context: Context){
//
//    val notificationManager = NotificationManagerCompat.from(context)
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val channel =
//            NotificationChannel(
//                Constants.NOTIFICATION_CHANNEL_ID,
//                Constants.NOTIFICATION_CHANNEL_NAME,
//                NotificationManager.IMPORTANCE_HIGH
//            )
//        notificationManager.createNotificationChannel(channel)
//    }
//    val notification = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
//        .setContentTitle("Scholar Navigate")
//        .setContentText("00:00:00")
//        .setSmallIcon(R.drawable.applogo)
//        .setPriority(NotificationCompat.PRIORITY_HIGH)
//        .setOngoing(true)
//        .build()
//    if (ActivityCompat.checkSelfPermission(
//            context,
//            Manifest.permission.POST_NOTIFICATIONS
//        ) != PackageManager.PERMISSION_GRANTED
//    ) {
//        return
//    }
//    notificationManager.notify(1, notification)
//}