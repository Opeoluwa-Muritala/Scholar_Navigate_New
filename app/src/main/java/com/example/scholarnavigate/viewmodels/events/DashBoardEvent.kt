package com.example.scholarnavigate.viewmodels.events

import androidx.compose.ui.graphics.Color
import com.example.scholarnavigate.data.module.Session
import com.example.scholarnavigate.data.module.Task

sealed class DashBoardEvent {
    data object SaveCourse : DashBoardEvent()

    data object DeleteCourse: DashBoardEvent()

    data class onDeleteSessionButtonClick(val session: Session): DashBoardEvent()

    data class onTaskIsCompleteChange(val task: Task): DashBoardEvent()

    data class onCourseCardColorChange(val colors: List<Color>): DashBoardEvent()

    data class onCourseNameChange(val name: String): DashBoardEvent()

    data class onGoalStudyHoursChange(val hours: String): DashBoardEvent()
}