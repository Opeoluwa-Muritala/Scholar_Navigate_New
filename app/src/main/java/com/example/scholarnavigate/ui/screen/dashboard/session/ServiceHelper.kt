package com.example.scholarnavigate.ui.screen.dashboard.session

import android.content.Context
import android.content.Intent

object ServiceHelper {
    fun triggerForeGroundService(context: Context, action: String){
        Intent(context, SessionTimerService::class.java).apply {
            this.action = action
            context.stopService(this)
        }
    }
}