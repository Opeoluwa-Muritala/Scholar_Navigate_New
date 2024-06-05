package com.example.scholarnavigate.viewmodels.events

import androidx.compose.ui.graphics.Color
import com.example.scholarnavigate.data.module.Session
import com.example.scholarnavigate.data.module.Task

sealed class CourseEvent {
    data object DeleteCourse: CourseEvent()
    data object UpdateSubject: CourseEvent()
    data object DeleteSession: CourseEvent()
    data object UpdateProgress: CourseEvent()
    data class OnTaskIsCompleteChange(val task: Task): CourseEvent()
    data class OnCourseCardColorChange(val colors: List<Color>): CourseEvent()
    data class OnDeleteSessionButtonClick(val session: Session): CourseEvent()
    data class OnCourseCardNameChange(val name: String): CourseEvent()
    data class OnGoalStudyHoursChange(val hours: String): CourseEvent()
}