package com.example.scholarnavigate.data.uimodule

import androidx.compose.ui.graphics.Color
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.module.Session
import com.example.scholarnavigate.data.module.Task

data class CourseState(
    val currentCourseId: Int? = null,
    val courseName: String = "",
    val goalStudyHours : String = "",
    val syllabus: String = "",
    val studiedHours: Float = 0f,
    val courseCardColors: List<Color> = Course.subjectCardColors.first(),
    val recentSession: List<Session> = emptyList(),
    val upcomingTask: List<Task> = emptyList(),
    val completedTask: List<Task> = emptyList(),
    val session: Session? = null,
    val progress: Float = 0f,
)
