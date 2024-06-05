package com.example.scholarnavigate.data.uimodule

import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.util.Priority

data class TaskState(
    val title: String = "",
    val description: String = "",
    val dueDate: Long? = null,
    val isTaskComplete: Boolean = false,
    val priority: Priority = Priority.LOW,
    val relatedToSubject: String? = null,
    val subjects: List<Course> = emptyList(),
    val courseId: Int? = null,
    val currentTaskId: Int? = null
)
