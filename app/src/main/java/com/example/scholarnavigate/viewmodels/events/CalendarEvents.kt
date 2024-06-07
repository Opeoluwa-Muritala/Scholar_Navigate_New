package com.example.scholarnavigate.viewmodels.events

import com.example.scholarnavigate.data.module.Task

sealed class CalendarEvents {
    data class OnTaskIsCompleteChange(val task: Task): CalendarEvents()
}