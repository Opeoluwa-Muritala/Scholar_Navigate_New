package com.example.scholarnavigate.data.uimodule

import androidx.compose.ui.graphics.Color
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.module.Session

data class DashboardState(
    val totalCourseCount: Int = 0,
    val totalStudiedHours: Float = 0f,
    val totalGoalStudyHours: Float = 0f,
    val courses: List<Course> = emptyList(),
    val courseName: String = "",
    val goalStudyHours: String = "",
    val courseCardColors: List<Color> = Course.subjectCardColors.random(),
    val session: Session? = null
)