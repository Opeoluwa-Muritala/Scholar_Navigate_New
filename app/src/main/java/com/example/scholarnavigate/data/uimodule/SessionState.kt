package com.example.scholarnavigate.data.uimodule

import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.module.Session

data class SessionState(
    val courses: List<Course> = emptyList(),
    val sessions: List<Session> = emptyList(),
    val relatedSubject: String? = null,
    val courseId: Int? = null,
    val session: Session? = null
)