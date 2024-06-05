package com.example.scholarnavigate.data.module

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.scholarnavigate.data.util.Priority

@Entity
data class Task(
    val title: String,
    val description: String,
    val dueDates: Long,
    val priority: Int,
    val relatedCourses: String,
    val isComplete: Boolean,
    val taskSubjectId: Int,
    @PrimaryKey(autoGenerate = true)
    val taskId: Int? = null
)