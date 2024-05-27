package com.example.scholarnavigate.data.module

data class Task(
    val title: String,
    val description: String,
    val dueDates: Long,
    val priority: Int,
    val relatedCourses: String,
    val isComplete: Boolean,
    val taskSubjectId: Int,
    val taskId: Int
)